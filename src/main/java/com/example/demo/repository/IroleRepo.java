package com.example.demo.repository;

import com.example.demo.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IroleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
