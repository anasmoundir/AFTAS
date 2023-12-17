package com.example.demo.repository;

import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Member;
import com.example.demo.model.entities.Rankin;
import com.example.demo.model.entities.RankingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IrankinRepo extends JpaRepository<Rankin, RankingId>
{

    @Query("SELECT r FROM Rankin r WHERE r.member = :member AND r.competition = :competition")
    Rankin findByMemberAndCompetition(
            @Param("member") Member member,
            @Param("competition") Competition competition
    );

}
