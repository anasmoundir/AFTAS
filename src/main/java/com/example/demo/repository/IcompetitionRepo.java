package com.example.demo.repository;

import com.example.demo.model.entities.Competition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface IcompetitionRepo extends PagingAndSortingRepository<Competition, Long> {
    @Query("SELECT comp FROM Competition comp " +
            "WHERE comp.theDate = :yesterday " +
            "AND comp.startTime < :endTimeOfDay")
    Page<Competition> findOpenCompetitions(
            @Param("yesterday") LocalDate yesterday,
            @Param("endTimeOfDay") LocalTime endTimeOfDay,
            Pageable pageable);

    @Query("SELECT comp FROM Competition comp " +
            "WHERE (comp.theDate < :currentDate OR (comp.theDate = :currentDate AND comp.endTime <= :endTimeOfDay)) " +
            "AND comp.endTime > :currentTimestamp")
    Page<Competition> findClosedCompetitions(
            @Param("currentDate") LocalDate currentDate,
            @Param("endTimeOfDay") LocalTime endTimeOfDay,
            @Param("currentTimestamp") LocalDateTime currentTimestamp,
            Pageable pageable);


    Optional<Object> findById(Long id);

    void save(Competition competition);

    void deleteById(Long id);

    Page<Competition> findAll();
}
