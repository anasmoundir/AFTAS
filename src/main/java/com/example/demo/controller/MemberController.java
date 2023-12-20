package com.example.demo.controller;

import com.example.demo.Exeption.PersonNotFoundException;
import com.example.demo.Util.ResponseManager;
import com.example.demo.model.entities.dto.CompetitionDto;
import com.example.demo.model.entities.dto.MemberDto;

import com.example.demo.model.entities.mapper.MyMapperImp;
import com.example.demo.repository.IrankinRepo;
import com.example.demo.service.CompetitionService;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/member")
@RestControllerAdvice
@CrossOrigin(origins = "http://localhost:4200")
public class MemberController {
    private  final MemberService memberService;
    private  final CompetitionService competitionService;
    private final IrankinRepo irankinRepo;
    private MyMapperImp myMapperImp;

    @Autowired
    public MemberController( MemberService memberService,CompetitionService competitionService, MyMapperImp  myMapperImp,
                             IrankinRepo irankinRepo)
    {
        this.memberService = memberService;
        this.competitionService =competitionService;
        this.myMapperImp = myMapperImp;
        this.irankinRepo = irankinRepo;
    }
    @GetMapping("/{id}")
    public ResponseEntity<MemberDto>  getMemberById(@PathVariable Long id)
    {
        try{
        MemberDto memberDto = memberService.getMemberById(id);
        return  ResponseEntity.ok(memberDto);
    }
    catch (Exception e) {
        throw e;
    }
    }
    @GetMapping("/All")
    public ResponseEntity<Page<MemberDto>> getAllMembers(Pageable pageable) {
        Page<MemberDto> membersPage = memberService.getAllMembers(pageable);
        return ResponseEntity.ok(membersPage);
    }
    @PostMapping
    public ResponseEntity<MemberDto> addMember(@RequestBody MemberDto memberDto)
    {
        MemberDto memberDto1 = memberService.addMember(memberDto);
        return ResponseEntity.ok(memberDto1);
    }
    @GetMapping("/members/searchByName")
    public List<MemberDto> searchMembersByName(@RequestParam String name) {
        return memberService.searchMembersByName(name);
    }

    @GetMapping("/members/searchByFamilyname")
    public List<MemberDto> searchMembersByFamilyname(@RequestParam String familyname) {
        return memberService.searchMembersByFamilyname(familyname);
    }

    @GetMapping("/members/searchByNum")
    public List<MemberDto> searchMembersByNum(@RequestParam Long num) {
        return memberService.searchMembersByNum(num);
    }
    @PostMapping("/{memberId}/competitions/{competitionId}/register")
    public ResponseEntity<String> registerMemberInCompetition(@PathVariable Long memberId, @PathVariable Long competitionId) {
        MemberDto member = memberService.getMemberById(memberId);
        CompetitionDto competition = competitionService.getCompetitionById(competitionId);

        if (member == null || competition == null) {
            return ResponseEntity.badRequest().build();
        }

        if (memberService.registerMemberInCompetition(myMapperImp.memberDtoToMember(member), myMapperImp.competitionDtoToCompetition(competition))) {
            return ResponseManager.good("good");
        } else {

            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable Long id, @RequestBody MemberDto memberDto) {
        try {
            memberService.updateMember(id, memberDto);
            return ResponseEntity.ok().build();
        } catch (PersonNotFoundException e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        try {
            memberService.deleteMember(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


