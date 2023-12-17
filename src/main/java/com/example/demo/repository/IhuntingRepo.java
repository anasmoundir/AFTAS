package com.example.demo.repository;

import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Hunting;
import com.example.demo.model.entities.Member;
import com.example.demo.model.entities.Rankin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IhuntingRepo extends JpaRepository<Hunting,Long> {
    Hunting findByFishIdAndMemberId(Long fishId, Long memberId);

    Rankin findByMemberAndCompetition(Member member, Competition competition);
}
