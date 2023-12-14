package com.example.demo.service.serviceImp;

import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Fish;
import com.example.demo.model.entities.Hunting;
import com.example.demo.model.entities.Member;
import com.example.demo.model.entities.dto.HuntingDto;
import com.example.demo.model.entities.mapper.HuntingMapper;
import com.example.demo.repository.IcompetitionRepo;
import com.example.demo.repository.IfishRepo;
import com.example.demo.repository.IhuntingRepo;
import com.example.demo.repository.ImemberRepo;
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
    private  final IfishRepo ifishRepo;
    private final  ImemberRepo imemberRepo;
    private final IcompetitionRepo icompetitionRepo;

    @Autowired
    public HuntingServiceImpl(IhuntingRepo huntingRepo, HuntingMapper huntingMapper,IfishRepo ifishRepo, ImemberRepo imemberRepo,
                              IcompetitionRepo icompetitionRepo) {
        this.huntingRepo = huntingRepo;
        this.huntingMapper = huntingMapper;
        this.ifishRepo = ifishRepo;
        this.imemberRepo =imemberRepo;
        this.icompetitionRepo = icompetitionRepo;
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
    public HuntingDto createHunting(HuntingDto huntingDto) {
        Competition competition = icompetitionRepo.findById(huntingDto.getCompetitionId())
                .orElseThrow(() -> new EntityNotFoundException("Competition not found with ID: " + huntingDto.getCompetitionId()));

        Fish fish = ifishRepo.findById(huntingDto.getFishId())
                .orElseThrow(() -> new EntityNotFoundException("Fish not found with ID: " + huntingDto.getFishId()));

        Member member = imemberRepo.findById(huntingDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + huntingDto.getMemberId()));

        Hunting hunting = huntingMapper.HuntingDtoToHunting(huntingDto);
        hunting.setCompetition(competition);
        hunting.setFish(fish);
        hunting.setMember(member);

        Hunting savedHunting = huntingRepo.save(hunting);

        return huntingMapper.HuntingToHuntingDto(savedHunting);
    }

    @Override
    public HuntingDto updateHunting(Long id, HuntingDto huntingDto) {
        // Implement update logic
        // You may need to fetch the entity, update fields, and save it
        // For simplicity, this is left as an exercise
        throw new UnsupportedOperationException("Update operation not implemented yet");
    }

    @Override
    public void deleteHunting(Long id) {
        huntingRepo.deleteById(id);
    }
}
