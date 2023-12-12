package com.example.demo.repository;

import com.example.demo.model.entities.Hunting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IhuntingRepo extends JpaRepository<Hunting,Long> {
}
