package com.example.demo.service.serviceImp;

import com.example.demo.model.entities.*;
import com.example.demo.model.entities.dto.CompetitionDto;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class HuntingServiceImpl implements HuntingService {
    private final IhuntingRepo huntingRepo;
    private final HuntingMapper huntingMapper;
    private final  IfishRepo fishrepo;
    private final IcompetitionRepo icompetitionRepo;
    private final MemberServiceImpl memberService;
    private final MyMapperImp mapperImp;
    private  final IrankinRepo rankrepo;
    private  final RankinServiceImpl rankinService;

    @Autowired
    public HuntingServiceImpl( MemberServiceImpl memberService,IhuntingRepo huntingRepo, HuntingMapper huntingMapper,RankinServiceImpl rankinService, ImemberRepo imemberRepo,
                              IcompetitionRepo icompetitionRepo,MyMapperImp mapperImp,IrankinRepo rankrepo,IfishRepo fishrepo) {
        this.huntingRepo = huntingRepo;
        this.huntingMapper = huntingMapper;
        this.rankinService =rankinService;
        this.icompetitionRepo = icompetitionRepo;
        this.memberService =memberService;
        this.mapperImp = mapperImp;
        this.rankrepo =rankrepo;
        this.fishrepo =fishrepo;
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
    public Hunting createHunting(HuntingDto huntingDto) {
        try {
            Member member = mapperImp.memberDtoTomember(memberService.getMemberById(huntingDto.getMemberId()));
            Fish fish = fishrepo.findById(huntingDto.getFishId())
                    .orElseThrow(() -> new EntityNotFoundException("Fish not found with ID: " + huntingDto.getFishId()));
            Competition competition = icompetitionRepo.findById(huntingDto.getCompetitionId())
                    .orElseThrow(() -> new EntityNotFoundException("Competition not found with ID: " + huntingDto.getCompetitionId()));
            Level level = fish.getLevel();
            int points = level.getPoints() * huntingDto.getNombreOffish();


            if (member != null && fish != null && competition != null) {
                Hunting hunting = new Hunting(member, fish, competition, huntingDto.getNombreOffish());
                huntingRepo.save(hunting);
                updateRanking(member, competition, points);
                return hunting;
            } else {
                throw new RuntimeException("One or more entities (Member, Fish, Competition) are null");
            }
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Entity not found", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while creating hunting", e);
        }
    }
    private void updateRanking(Member member, Competition competition, int points) {
        Rankin ranking = rankrepo.findByMemberAndCompetition(member, competition);
        if(ranking == null)
        {
            try
            {
                memberService.registerMemberInCompetition(member,competition);
                Rankin rankingnew = rankrepo.findByMemberAndCompetition(member, competition);
                rankingnew.setScore(rankingnew.getScore() + points);
                rankinService.calculateRank(rankingnew);
                rankrepo.save(rankingnew);
            }catch (Exception e){
                throw e;
            }
        }else {
            ranking.setScore(ranking.getScore() + points);
            rankinService.calculateRank(ranking);
            rankrepo.save(ranking);
        }
    }

    @Override
    public HuntingDto addHuntingAndCalculateScore(HuntingDto huntingDto) {
        Hunting createdHunting = createHunting(huntingDto);
        updateMemberScore(createdHunting);

        return huntingMapper.HuntingToHuntingDto(createdHunting);
    }

    private void updateMemberScore(Hunting hunting) {
        MemberDto existingMember = memberService.getMemberById(hunting.getMember().getId());
        rankinService.updateRankingAndScore(hunting);
        memberService.updateMember(existingMember.getId(), existingMember);
    }
    @Override
    public HuntingDto updateHunting(Long id, HuntingDto huntingDto) {
        Hunting updatedHunting = huntingRepo.findById(id)
                .map(hunting -> {
                    hunting.setNombreOffish(huntingDto.getNombreOffish());
                    return huntingRepo.save(hunting);
                })
                .orElseThrow(() -> new EntityNotFoundException("Hunting not found with id: " + id));
        updateMemberScore(updatedHunting);
        return huntingMapper.HuntingToHuntingDto(updatedHunting);
    }

    @Override
    public void deleteHunting(Long id) {
        huntingRepo.deleteById(id);
    }

}
