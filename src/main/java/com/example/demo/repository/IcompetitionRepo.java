package com.example.demo.repository;

import com.example.demo.model.entities.Competition;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface IcompetitionRepo extends PagingAndSortingRepository<Competition, Long> {

    @Query("SELECT comp FROM Competition comp " +
            "WHERE comp.theDate >= :currentDate " +
            "OR (comp.theDate = :currentDate AND comp.endTime > :currentTimestamp) " +
            "ORDER BY comp.theDate DESC")
    List<Competition> findOpenCompetitionsForRegistration(
            @Param("currentDate") LocalDate currentDate,
            @Param("currentTimestamp") LocalTime currentTimestamp);
@Query("SELECT comp FROM Competition comp " +
        "WHERE comp.theDate >= :currentDate " +
        "OR (comp.theDate = :currentDate AND comp.endTime > :currentTimestamp) " +
        "ORDER BY comp.theDate DESC")
Page<Competition> findOpenCompetitions(
        @Param("currentDate") LocalDate currentDate,
        @Param("currentTimestamp") LocalTime currentTimestamp,
        Pageable pageable);


Page<Competition> findByTheDateBeforeAndEndTimeAfterOrderByTheDateDesc(
        LocalDate currentDate,
        LocalTime currentTimestamp,
        Pageable pageable
);

    Optional<Object> findById(Long id);

    void save(Competition competition);

    void deleteById(Long id);

    Page<Competition> findAll(Pageable pageable);

    @Modifying
    @Query("UPDATE Competition c SET c.numberOfParticipant = :newValue WHERE c.id = :competitionId")
    @Transactional
    void updateNumberOfParticipants(@Param("competitionId") Long competitionId, @Param("newValue") int newValue);


}
