package com.example.demo.repository;
import com.example.demo.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IuserRepo extends JpaRepository<User, Long> {
    User findByUsernameOrEmail(String username, String email);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByUsername(String username);
}
