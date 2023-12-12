package com.example.demo.repository;

import com.example.demo.model.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImemberRepo extends JpaRepository<Member,Long> {
}
