package com.example.demo.repository;

import com.example.demo.model.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImemberRepo extends JpaRepository<Member,Long> {
    List<Member> findByNum(Long num);

    List<Member> findByNameContaining(String name);

    List<Member> findByFamilynameContaining(String familyname);
}
