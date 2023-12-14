package com.example.demo.service;

import ch.qos.logback.classic.model.processor.LogbackClassicDefaultNestedComponentRules;
import com.example.demo.model.entities.dto.MemberDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {
    MemberDto getMemberById(Long id);
    List<MemberDto> getALlMember();
    MemberDto addMember(MemberDto memberDto);
    void UpdateMember(Long id,MemberDto memberDto);
    void DeleteMember(Long id);

}
