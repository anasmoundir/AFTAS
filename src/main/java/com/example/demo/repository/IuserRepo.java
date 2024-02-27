package com.example.demo.repository;
import com.example.demo.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IuserRepo extends JpaRepository<User, Long> {
    User findByUsernameOrEmail(String username, String email);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = :enabled WHERE u.username = :username")
    void updateUserEnabledStatus(boolean enabled, String username);

    List<User> findByEnabledFalse();
}
