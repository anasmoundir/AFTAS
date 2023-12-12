package com.example.demo.repository;

import com.example.demo.model.entities.Rankin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IrankinRepo extends JpaRepository<Rankin,Long>
{

}
