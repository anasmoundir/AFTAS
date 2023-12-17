package com.example.demo.repository;

import com.example.demo.model.entities.Competition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;

import java.time.LocalTime;
import java.util.List;
import java.time.LocalDate;


@Repository
public interface IcompetitionRepo extends JpaRepository<Competition, Long> {
    @Query("SELECT comp FROM Competition comp " +
            "WHERE comp.theDate = :yesterday " +
            "AND comp.startTime < :endTimeOfDay")
    List<Competition> findOpenCompetitions(
            @Param("yesterday") LocalDate yesterday,
            @Param("endTimeOfDay") LocalTime endTimeOfDay
    );

    @Query("SELECT comp FROM Competition comp " +
            "WHERE (comp.theDate < :currentDate OR (comp.theDate = :currentDate AND comp.endTime <= :endTimeOfDay)) " +
            "AND comp.endTime > :currentTimestamp")
    List<Competition> findClosedCompetitions(
            @Param("currentDate") LocalDate currentDate,
            @Param("endTimeOfDay") LocalTime endTimeOfDay,
            @Param("currentTimestamp") LocalDateTime currentTimestamp
    );



}
