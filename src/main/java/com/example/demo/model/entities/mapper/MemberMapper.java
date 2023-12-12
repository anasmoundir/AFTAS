package com.example.demo.model.entities.mapper;

import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Member;
import com.example.demo.model.entities.dto.CompetitionDto;
import com.example.demo.model.entities.dto.MemberDto;

import java.util.List;

public interface MemberMapper {
     MemberDto memberTomemberDto(Member member);
     Member memberDtoTomember(MemberDto memberDto);
    List<MemberDto> membersToMemberDtos(List<Member> members);

}
