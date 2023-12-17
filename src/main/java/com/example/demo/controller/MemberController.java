package com.example.demo.controller;

import com.example.demo.Util.ResponseManager;
import com.example.demo.model.entities.dto.CompetitionDto;
import com.example.demo.model.entities.dto.MemberDto;
import com.example.demo.model.entities.mapper.mapperImplementation.MyMapperImp;
import com.example.demo.repository.IrankinRepo;
import com.example.demo.service.CompetitionService;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/member")
@CrossOrigin(origins = "http://localhost:4200")
public class MemberController {
    private  final MemberService memberService;
    private  final CompetitionService competitionService;
    private  final MyMapperImp  myMapperImp;
    private final IrankinRepo irankinRepo;

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
        MemberDto memberDto = memberService.getMemberById(id);
        return  ResponseEntity.ok(memberDto);
    }
    @GetMapping("/All")
    public  ResponseEntity<List<MemberDto>> getAllMembers()
    {
        List<MemberDto> memberDtos = memberService.getALlMember();
        ResponseManager.notFound("no member found here add one per favor");
      return ResponseEntity.ok(memberDtos);
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

        if (memberService.registerMemberInCompetition(myMapperImp.memberDtoTomember(member), myMapperImp.competitionDtoToCompetition(competition))) {
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
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
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


