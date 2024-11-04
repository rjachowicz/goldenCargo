package com.goldencargo.repository;

import com.goldencargo.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
  User findByUsername(String username);
  User findByEmail(String email);

}