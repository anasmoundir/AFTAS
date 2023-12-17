package com.example.demo.repository;

import com.example.demo.model.entities.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IfishRepo extends JpaRepository<Fish,Long> {

}
