package com.example.demo.repository;

import com.example.demo.model.entities.Competition;
import com.example.demo.model.entities.RankingId;
import com.example.demo.model.entities.dto.CompetitionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;


@Repository
public interface IcompetitionRepo extends JpaRepository<Competition, Long> {
    @Query("SELECT comp FROM Competition comp WHERE " +
            "comp.theDate > :currentDate AND" +
            " comp.startTime > :futureTimestamp")
    List<Competition> findAvailableCompetitions(
            @Param("currentDate") LocalDate currentDate,
            @Param("futureTimestamp") LocalDateTime futureTimestamp
    );

    @Query("SELECT comp FROM Competition comp " +
            "WHERE (comp.theDate > :currentDate AND comp.startTime > :futureTimestamp) " +
            "OR comp.endTime <= :currentTimestamp")

    List<Competition> findOpenAndClosedCompetitions(
            @Param("currentDate") LocalDate currentDate,
            @Param("futureTimestamp") LocalDateTime futureTimestamp,
            @Param("currentTimestamp") LocalDateTime currentTimestamp
    );
}
