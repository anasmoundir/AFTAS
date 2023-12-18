package com.example.demo.model.entities.mapper.mapperImplementation;


import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Fish;
import com.example.demo.model.entities.Hunting;
import com.example.demo.model.entities.Member;
import com.example.demo.model.entities.dto.*;
import com.example.demo.model.entities.mapper.*;
import org.mapstruct.Mapper;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public class MyMapperImp implements CompetitionMapper, MemberMapper, RankinMapper, HuntingMapper, FishMapper,LevelMapper {


    @Override
    public CompetitionDto competitionToCompetitionDto(Competition competition) {
        CompetitionDto competitionDto = new CompetitionDto();
        competitionDto.setId(competition.getId());
        competitionDto.setAmount(competition.getAmount());
        competitionDto.setTheDate(competition.getTheDate());
        competitionDto.setNumberOfParticipant(competition.getNumberOfParticipant());
        competitionDto.setLocation(competition.getLocation());
        competitionDto.setEndTime(competitionDto.getEndTime());
        competitionDto.setStartTime(competitionDto.getStartTime());
        return competitionDto;
    }


        @Override
        public Competition competitionDtoToCompetition(CompetitionDto competitionDto) {
            if (competitionDto == null) {
                throw new IllegalArgumentException("Source competitionDto must not be null");
            }
            Competition competition = new Competition();
            BeanUtils.copyProperties(competitionDto, competition);
            return competition;
        }
    @Override
    public List<CompetitionDto> competitionsToCompetitionDtos(List<Competition> competitions) {
        List<CompetitionDto> competitionDtos = new ArrayList<>();
        for (Competition competition : competitions) {
            competitionDtos.add(competitionToCompetitionDto(competition));
        }
        return competitionDtos;
    }



    @Override
    public MemberDto memberTomemberDto(Member member) {
        MemberDto  memberDto = new MemberDto(member.getId(),member.getNum(),
                member.getName(),
                member.getFamilyname(),
                member.getAccessionDate(),
                member.getNationality(),
                member.getIdentitydocumenttype()
        );
        return memberDto;
    }

    @Override
    public Member memberDtoTomember(MemberDto memberDto) {
        Member member = new Member();
        member.setId(memberDto.getId());
        member.setName(memberDto.getName());
        member.setNum(memberDto.getNum());
        member.setNationality(memberDto.getNationality());
        member.setFamilyname(memberDto.getFamilyname());
        member.setAccessionDate(memberDto.getAccessionDate());
        member.setIdentitydocumenttype(memberDto.getIdentitydocumenttype());
        return member;
    }

    @Override
    public List<MemberDto> membersToMemberDtos(List<Member> members) {
        List<MemberDto> memberDtoLists = new ArrayList<>();
        for(Member member :members)
        {
            memberDtoLists.add(memberTomemberDto(member));
        }
        return memberDtoLists;
    }

    @Override
    public HuntingDto HuntingToHuntingDto(Hunting hunting) {
        HuntingDto huntingDto = new HuntingDto();
        huntingDto.setNombreOffish(hunting.getNombreOffish());
        huntingDto.setCompetitionId(hunting.getCompetition().getId());
        huntingDto.setFishId(hunting.getFish().getId());
        huntingDto.setMemberId(hunting.getMember().getId());
        return huntingDto;
    }

    @Override
    public Hunting HuntingDtoToHunting(HuntingDto huntingDto) {
        Hunting hunting = new Hunting();
        hunting.setNombreOffish(huntingDto.getNombreOffish());
        if (huntingDto.getCompetitionId() != null) {
            Competition competition = new Competition();
            competition.setId(huntingDto.getCompetitionId());
            hunting.setCompetition(competition);
        }
        if (huntingDto.getFishId() != null) {
            Fish fish = new Fish();
            fish.setId(huntingDto.getFishId());
            hunting.setFish(fish);
        }
        if (huntingDto.getMemberId() != null) {
            Member member = new Member();
            member.setId(huntingDto.getMemberId());
            hunting.setMember(member);
        }

        return hunting;
    }
    @Override
    public List<HuntingDto> HuntingsToHuntingDto(List<Hunting> huntings) {
        List<HuntingDto>  huntingDtos = new ArrayList<>();
        for(Hunting hunting : huntings)
        {
            huntingDtos.add(HuntingToHuntingDto(hunting));
        }
        return huntingDtos;
    }

    @Override
    public FishDto fishToFishDto(Fish fish) {
        if (fish == null) {
            return null;
        }

        FishDto fishDto = new FishDto();
        fishDto.setId(fish.getId());
        fishDto.setName(fish.getName());
        fishDto.setAverageweight(fish.getAverageweight());

        if (fish.getLevel() != null) {
            fishDto.setLevel(levelToLevelDto(fish.getLevel()));
        }

        return fishDto;
    }

    @Override
    public Fish fishDtoToFish(FishDto fishDto) {
        if (fishDto == null) {
            return null;
        }

        Fish fish = new Fish();
        fish.setId(fishDto.getId());
        fish.setName(fishDto.getName());
        fish.setAverageweight(fishDto.getAverageweight());

        if (fishDto.getLevel() != null) {
            fish.setLevel(levelDtoToLevel(fishDto.getLevel()));
        }

        return fish;
    }

    @Override
    public List<FishDto> fishsToFishDto(List<Fish> fishs) {
        if (fishs == null) {
            return null;
        }

        return fishs.stream()
                .map(this::fishToFishDto)
                .collect(Collectors.toList());
    }

    @Override
    public LevelDto levelToLevelDto(com.example.demo.model.entities.Level level) {
        if (level == null) {
            return null;
        }

        LevelDto levelDto = new LevelDto();
        levelDto.setId(level.getId());
        levelDto.setDescription(level.getDescription());
        levelDto.setPoints(level.getPoints());
        return levelDto;
    }

    @Override
    public com.example.demo.model.entities.Level levelDtoToLevel(LevelDto levelDto) {
        if (levelDto == null) {
            return null;
        }
        com.example.demo.model.entities.Level level = new com.example.demo.model.entities.Level();
        level.setId(levelDto.getId());
        level.setDescription(levelDto.getDescription());
        level.setPoints(levelDto.getPoints());
        return level;
    }
}
