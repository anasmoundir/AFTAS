package com.example.demo.service;

import ch.qos.logback.classic.model.processor.LogbackClassicDefaultNestedComponentRules;
import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Member;
import com.example.demo.model.entities.dto.MemberDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {
    MemberDto getMemberById(Long id);
    List<MemberDto> getALlMember();
    MemberDto addMember(MemberDto memberDto);



    List<MemberDto> searchMembersByName(String name);

    List<MemberDto> searchMembersByFamilyname(String familyname);

    List<MemberDto> searchMembersByNum(Long num);

    void updateMember(Long id, MemberDto memberDto);

    void deleteMember(Long id);

    boolean registerMemberInCompetition(Member member, Competition competition);
}
