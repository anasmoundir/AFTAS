package com.example.demo.service.serviceImp;

import com.example.demo.model.entities.*;
import com.example.demo.model.entities.dto.HuntingDto;
import com.example.demo.model.entities.dto.MemberDto;
import com.example.demo.model.entities.mapper.HuntingMapper;
import com.example.demo.model.entities.mapper.mapperImplementation.MyMapperImp;
import com.example.demo.repository.*;
import com.example.demo.service.HuntingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HuntingServiceImpl implements HuntingService {
    private final IhuntingRepo huntingRepo;
    private final HuntingMapper huntingMapper;

    private final IcompetitionRepo icompetitionRepo;
    private final MemberServiceImpl memberService;
    private final MyMapperImp mapperImp;
    private  final IrankinRepo rankrepo;
    private  final RankinServiceImpl rankinService;

    @Autowired
    public HuntingServiceImpl( MemberServiceImpl memberService,IhuntingRepo huntingRepo, HuntingMapper huntingMapper,RankinServiceImpl rankinService, ImemberRepo imemberRepo,
                              IcompetitionRepo icompetitionRepo,MyMapperImp mapperImp,IrankinRepo rankrepo) {
        this.huntingRepo = huntingRepo;
        this.huntingMapper = huntingMapper;
        this.rankinService =rankinService;
        this.icompetitionRepo = icompetitionRepo;
        this.memberService =memberService;
        this.mapperImp = mapperImp;
        this.rankrepo =rankrepo;
    }

    @Override
    public List<HuntingDto> getAllHuntings() {
        List<Hunting> huntings = huntingRepo.findAll();
        return huntings.stream()
                .map(huntingMapper::HuntingToHuntingDto)
                .collect(Collectors.toList());
    }

    @Override
    public HuntingDto getHuntingById(Long id) {
        Hunting hunting = huntingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hunting not found with id: " + id));
        return huntingMapper.HuntingToHuntingDto(hunting);
    }

    @Override
    public Hunting createHunting(Member member, Fish fish) {
        try {
            Level level = fish.getLevel();
            int points = level.getPoints();
            Hunting hunting = new Hunting(member, fish, points);
            huntingRepo.save(hunting);

            Competition competition = hunting.getCompetition();
            Rankin ranking = huntingRepo.findByMemberAndCompetition(member, competition);
            if (ranking == null) {
                ranking = new Rankin(member, competition);
            }
            ranking.setScore(ranking.getScore() + points);
            rankinService.calculateRank(ranking);
            rankrepo.save(ranking);

            return hunting;
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Entity not found", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while creating hunting", e);
        }
    }
    @Override
    public HuntingDto updateHunting(Long id, HuntingDto huntingDto) {
        // Implement update logic
        // You may need to fetch the entity, update fields, and save it
        // For simplicity, this is left as an exercise
        throw new UnsupportedOperationException("Update operation not implemented yet");
    }

    @Override
    public HuntingDto addHuntingAndCalculateScore(HuntingDto huntingDto) {
        HuntingDto createdHunting = createHunting(huntingDto);
        updateMemberScore(createdHunting);
        return createdHunting;
    }

    private void updateMemberScore(HuntingDto huntingDto) {
        Hunting  hunting = mapperImp.HuntingDtoToHunting(huntingDto);
        Long membeDTOId = huntingDto.getMemberId();
        MemberDto existingMember = memberService.getMemberById(membeDTOId);
        Member member = mapperImp.memberDtoTomember(existingMember);
        rankinService.updateRankingAndScore(hunting);
        memberService.updateMember(existingMember.getId(), existingMember);
    }


    @Override
    public void deleteHunting(Long id) {
        huntingRepo.deleteById(id);
    }

    @Override
    public HuntingDto createHunting(HuntingDto huntingDto) {
        return null;
    }
}
