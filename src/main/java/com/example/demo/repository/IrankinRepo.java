package com.example.demo.repository;

import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.Member;
import com.example.demo.model.entities.Rankin;
import com.example.demo.model.entities.RankingId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IrankinRepo extends JpaRepository<Rankin, RankingId>
{
    @Modifying
    @Transactional
    @Query("UPDATE Rankin r SET r.rank = :newRank WHERE r.id = :rankingId")
    void updateRank(@Param("rankingId") RankingId rankingId, @Param("newRank") int newRank);

    @Query("SELECT r FROM Rankin r WHERE r.member = :member AND r.competition = :competition")
    Rankin findByMemberAndCompetition(
            @Param("member") Member member,
            @Param("competition") Competition competition
    );

    @Query("SELECT r FROM Rankin r WHERE r.competition = :competition ORDER BY r.rank ASC")
    List<Rankin> findTopThreeByCompetition(@Param("competition") Competition competition);
}
