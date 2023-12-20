package com.example.demo.repository;

import com.example.demo.model.entities.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImemberRepo extends PagingAndSortingRepository<Member,Long> {
    List<Member> findByNum(Long num);

    List<Member> findByNameContaining(String name);
    Page<Member> findAll(Pageable pageable);

    List<Member> findByFamilynameContaining(String familyname);

    Optional<Member> findById(Long id);

    void save(Member member);

    void deleteById(Long id);
}
