package com.example.demo.service.serviceImp;

import com.example.demo.model.entities.Member;
import com.example.demo.model.entities.dto.MemberDto;
import com.example.demo.model.entities.mapper.CompetitionMapper;
import com.example.demo.model.entities.mapper.MemberMapper;
import com.example.demo.model.entities.mapper.mapperImplementation.MyMapperImp;
import com.example.demo.repository.IcompetitionRepo;
import com.example.demo.repository.ImemberRepo;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.util.List;

@Component
public class MemberServiceImpl implements MemberService {

    private final ImemberRepo  imemberRepo;
    private final MyMapperImp myMapperImp;

    @Autowired
    public  MemberServiceImpl(ImemberRepo  imemberRepo,
                              MyMapperImp myMapperImp)
    {
        this.imemberRepo =imemberRepo;
        this.myMapperImp = myMapperImp;
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
    public void UpdateMember(Long id, MemberDto memberDto) {

    }

    @Override
    public void DeleteMember(Long id) {

    }
}
