package com.example.demo.service.serviceImp;

import com.example.demo.Util.ResponseManager;
import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Member;
import com.example.demo.model.entities.Rankin;
import com.example.demo.model.entities.RankingId;
import com.example.demo.model.entities.dto.MemberDto;
import com.example.demo.model.entities.mapper.mapperImplementation.MyMapperImp;
import com.example.demo.repository.ImemberRepo;
import com.example.demo.repository.IrankinRepo;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class MemberServiceImpl implements MemberService {

    private final ImemberRepo  imemberRepo;
    private final MyMapperImp myMapperImp;
    private  final  IrankinRepo irankinRepo;

    @Autowired
    public  MemberServiceImpl(ImemberRepo  imemberRepo,
                              MyMapperImp myMapperImp, IrankinRepo irankinRepo)
    {
        this.imemberRepo =imemberRepo;
        this.myMapperImp = myMapperImp;
        this.irankinRepo = irankinRepo;
    }


    @Override
    public MemberDto getMemberById(Long id) {
        Member member = imemberRepo.findById(id).orElseThrow();
        return myMapperImp.memberTomemberDto(member);
    }

    @Override
    public List<MemberDto> getALlMember() {
        List<Member> members =  imemberRepo.findAll();
        return myMapperImp.membersToMemberDtos(members);
    }

    @Override
    public MemberDto addMember(MemberDto memberDto) {
        Member  member = myMapperImp.memberDtoTomember(memberDto);
         imemberRepo.save(member);
         return myMapperImp.memberTomemberDto(member);
    }

    @Override
    public List<MemberDto> searchMembersByName(String name) {
        List<Member> foundMembers = imemberRepo.findByNameContaining(name);
        return myMapperImp.membersToMemberDtos(foundMembers);
    }

    @Override
    public List<MemberDto> searchMembersByFamilyname(String familyname) {
        List<Member> foundMembers = imemberRepo.findByFamilynameContaining(familyname);
        return myMapperImp.membersToMemberDtos(foundMembers);
    }

    @Override
    public List<MemberDto> searchMembersByNum(Long num) {
        List<Member> foundMembers = imemberRepo.findByNum(num);
        return myMapperImp.membersToMemberDtos(foundMembers);
    }


    @Override
    public void updateMember(Long id, MemberDto memberDto) {
        Optional<Member> existingMember = imemberRepo.findById(id);

        if (existingMember.isPresent()) {
            Member memberToUpdate = existingMember.get();

            if (memberDto.getName() != null) {
                memberToUpdate.setName(memberDto.getName());
            }

            if (memberDto.getFamilyname() != null) {
                memberToUpdate.setFamilyname(memberDto.getFamilyname());
            }


            imemberRepo.save(memberToUpdate);
        } else {

            throw new NoSuchElementException("Member not found with id: " + id);
        }        }



    @Override
    public void deleteMember(Long id) {
        Optional<Member> existingMember = imemberRepo.findById(id);

        if (existingMember.isPresent()) {
            imemberRepo.deleteById(id);
        } else {

            throw new NoSuchElementException("Member not found with id: " + id);
        }
        }

    @Override
    public boolean registerMemberInCompetition(Member member, Competition competition) {
        if (member == null || competition == null) {
            return ResponseManager.badRequest("Invalid member or competition").hasBody();
        }

        RankingId rankin = new RankingId();
        rankin.setMemberId(member.getId());
        rankin.setCompetitionId(competition.getId());
        Optional<Rankin> existingRankin = irankinRepo.findById(rankin);
//        if(existingRankin.isPresent()){
        Rankin rankin1 =new Rankin();
        rankin1.setId(rankin);
        rankin1.setRank(0);
        rankin1.setScore(0.0);
        irankinRepo.save(rankin1);
        return true;
//    }
//        else return false;
    }

    }

