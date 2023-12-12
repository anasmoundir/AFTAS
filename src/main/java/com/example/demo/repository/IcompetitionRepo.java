package com.example.demo.repository;

import com.example.demo.model.entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IcompetitionRepo extends JpaRepository<Competition,Long> {
}
