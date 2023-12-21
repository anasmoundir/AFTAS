package com.example.demo.service.serviceImp;

import com.example.demo.Exeption.CompetitionNotFoundException;
import com.example.demo.Exeption.DuplicateRegistrationException;
import com.example.demo.Exeption.PersonNotFoundException;
import com.example.demo.Util.ResponseManager;
import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Member;
import com.example.demo.model.entities.Rankin;
import com.example.demo.model.entities.RankingId;
import com.example.demo.model.entities.dto.MemberDto;
import com.example.demo.model.entities.mapper.MyMapperImp;
import com.example.demo.repository.IcompetitionRepo;
import com.example.demo.repository.IfishRepo;
import com.example.demo.repository.ImemberRepo;
import com.example.demo.repository.IrankinRepo;
import com.example.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
public class MemberServiceImpl implements MemberService {

    private final ImemberRepo  imemberRepo;
    private final MyMapperImp myMapperImp;
    private  final  IrankinRepo irankinRepo;

    private final IcompetitionRepo icompetitionRepo;
    @Autowired
    public  MemberServiceImpl( ImemberRepo  imemberRepo,
                              MyMapperImp myMapperImp, IrankinRepo irankinRepo,CompetitionServiceImpl competitionService,
                              IcompetitionRepo icompetitionRepo)
    {
        this.imemberRepo =imemberRepo;
        this.myMapperImp = myMapperImp;
        this.irankinRepo = irankinRepo;
        this.icompetitionRepo = icompetitionRepo;
    }


    @Override
    public MemberDto getMemberById(Long id) {
        Optional<Member> optionalMember = imemberRepo.findById(id);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return myMapperImp.memberToMemberDto(member);
        } else {
            throw new PersonNotFoundException("Person not found with id: " + id);
        }
    }

    @Override
    public Page<MemberDto> getAllMembers(Pageable pageable) {
        Page<Member> membersPage = imemberRepo.findAll(pageable);
        return membersPage.map(myMapperImp::memberToMemberDto);
    }


    @Override
    public MemberDto addMember(MemberDto memberDto) {
        Member  member = myMapperImp.memberDtoToMember(memberDto);
        member.setNum((int) (Instant.now().toEpochMilli() % 1_000_000) + (int) (Math.random() * 1_000_000));
         imemberRepo.save(member);
         return myMapperImp.memberToMemberDto(member);
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
        }
    }


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

        LocalDate today = LocalDate.now();
        LocalTime startTime = LocalTime.now().plusHours(24);

        List<Competition> openCompetitionsList = icompetitionRepo.findOpenCompetitionsForRegistration(today, startTime);

        if (openCompetitionsList != null && openCompetitionsList.stream().anyMatch(c -> c.getId().equals(competition.getId()))) {
            boolean isMemberRegistered = irankinRepo.existsById(new RankingId(member.getId(), competition.getId()));

            if (!isMemberRegistered) {
                Rankin rankin1 = new Rankin();
                RankingId rankin = new RankingId();
                rankin.setMemberId(member.getId());
                rankin.setCompetitionId(competition.getId());
                rankin1.setId(rankin);
                rankin1.setRank(0);
                rankin1.setScore(0.0);

                icompetitionRepo.updateNumberOfParticipants(competition.getId(), competition.getNumberOfParticipant() + 1);
                irankinRepo.save(rankin1);
            } else {

                throw new DuplicateRegistrationException("Member is already registered for this competition");
            }
        } else {
            throw new CompetitionNotFoundException("Competition not found or already closed");
        }

        return true;
    }

}

