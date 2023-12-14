package com.example.demo.controller;

import com.example.demo.model.entities.dto.MemberDto;
import com.example.demo.service.MemberService;
import liquibase.license.LicenseInstallResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/member")
public class MemberController {
    private  final MemberService memberService;
    @Autowired
    public MemberController( MemberService memberService)
    {
        this.memberService = memberService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<MemberDto>  getMemberById(@PathVariable Long id)
    {
        MemberDto memberDto = memberService.getMemberById(id);
        return  ResponseEntity.ok(memberDto);
    }
    @GetMapping("/All")
    public  ResponseEntity<List<MemberDto>> getAllMembers()
    {
        List<MemberDto> memberDtos = memberService.getALlMember();
        return ResponseEntity.ok(memberDtos);
    }
    @PostMapping
    public ResponseEntity<MemberDto> addMember(@RequestBody MemberDto memberDto)
    {
        MemberDto memberDto1 = memberService.addMember(memberDto);
        return ResponseEntity.ok(memberDto1);
    }


}
