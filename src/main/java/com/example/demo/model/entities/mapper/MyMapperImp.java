package com.example.demo.model.entities.mapper;

import com.example.demo.model.entities.*;
import com.example.demo.model.entities.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyMapperImp implements CompetitionMapper, MemberMapper, HuntingMapper, FishMapper, LevelMapper {

    private final ModelMapper modelMapper;

    //

    @Autowired
    public MyMapperImp(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public CompetitionDto competitionToCompetitionDto(Competition competition) {
        return modelMapper.map(competition, CompetitionDto.class);
    }
    @Override

    public Competition competitionDtoToCompetition(CompetitionDto competitionDto) {
        return modelMapper.map(competitionDto, Competition.class);
    }
    @Override

    public List<CompetitionDto> competitionsToCompetitionDtos(List<Competition> competitions) {
        return competitions.stream()
                .map(competition -> modelMapper.map(competition, CompetitionDto.class))
                .collect(Collectors.toList());
    }
    @Override

    public MemberDto memberToMemberDto(Member member) {
        return modelMapper.map(member, MemberDto.class);
    }
    @Override

    public Member memberDtoToMember(MemberDto memberDto) {
        return modelMapper.map(memberDto, Member.class);
    }
    @Override

    public List<MemberDto> membersToMemberDtos(List<Member> members) {
        return members.stream()
                .map(member -> modelMapper.map(member, MemberDto.class))
                .collect(Collectors.toList());
    }
    @Override

    public HuntingDto huntingToHuntingDto(Hunting hunting) {
        return modelMapper.map(hunting, HuntingDto.class);
    }
    @Override

    public Hunting huntingDtoToHunting(HuntingDto huntingDto) {
        return modelMapper.map(huntingDto, Hunting.class);
    }
    @Override

    public List<HuntingDto> huntingsToHuntingDtos(List<Hunting> huntings) {
        return huntings.stream()
                .map(hunting -> modelMapper.map(hunting, HuntingDto.class))
                .collect(Collectors.toList());
    }
    @Override

    public FishDto fishToFishDto(Fish fish) {
        return modelMapper.map(fish, FishDto.class);
    }
    @Override

    public Fish fishDtoToFish(FishDto fishDto) {
        return modelMapper.map(fishDto, Fish.class);
    }
    @Override

    public List<FishDto> fishToFishDtos(List<Fish> fishList) {
        return fishList.stream()
                .map(fish -> modelMapper.map(fish, FishDto.class))
                .collect(Collectors.toList());
    }
    @Override

    public LevelDto levelToLevelDto(Level level) {
        return modelMapper.map(level, LevelDto.class);
    }
    @Override

    public Level levelDtoToLevel(LevelDto levelDto) {
        return modelMapper.map(levelDto, Level.class);
    }
}