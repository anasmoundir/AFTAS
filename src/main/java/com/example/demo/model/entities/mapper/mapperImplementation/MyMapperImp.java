package com.example.demo.model.entities.mapper.mapperImplementation;


import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Member;
import com.example.demo.model.entities.dto.CompetitionDto;
import com.example.demo.model.entities.dto.MemberDto;
import com.example.demo.model.entities.mapper.CompetitionMapper;
import com.example.demo.model.entities.mapper.MemberMapper;
import com.example.demo.model.entities.mapper.RankinMapper;
import org.mapstruct.Mapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public class MyMapperImp implements CompetitionMapper, MemberMapper, RankinMapper {


    @Override
    public CompetitionDto competitionToCompetitionDto(Competition competition) {
        CompetitionDto competitionDto = new CompetitionDto();
        competitionDto.setId(competition.getId());
        competitionDto.setCode(competition.getCode());
        competitionDto.setAmount(competition.getAmount());
        competitionDto.setLocation(competition.getLocation());
        competitionDto.setEndTime(competitionDto.getEndTime());
        competitionDto.setStartTime(competitionDto.getStartTime());
        return competitionDto;
    }

    @Override
    public Competition competitionDtoToCompetition(CompetitionDto competitionDto) {
        Competition competition =new Competition();
        competition.setId(competitionDto.getId());
        competition.setLocation(competitionDto.getLocation());
        competition.setCode(competitionDto.getCode());
        competition.setAmount(competitionDto.getAmount());
        competition.setStartTime((Date) competitionDto.getStartTime());
        competition.setEndTime((Date) competitionDto.getStartTime());
        competition.setLocation(competitionDto.getLocation());
        competition.setNumberOfParticipant(competitionDto.getNumberOfParticipant());
        competition.setAmount(competitionDto.getAmount());
        return competition;
    }

    @Override
    public List<CompetitionDto> competitionsToCompetitionDtos(List<Competition> competitions) {
        List<CompetitionDto> competitionDtos = new ArrayList<>();
        for (Competition competition : competitions) {
            competitionDtos.add(competitionToCompetitionDto(competition));
        }
        return competitionDtos;    }

    @Override
    public MemberDto memberTomemberDto(Member member) {
        return null;
    }

    @Override
    public Member memberDtoTomember(MemberDto memberDto) {
        return null;
    }

    @Override
    public List<MemberDto> membersToMemberDtos(List<Member> members) {
        return null;
    }
}
