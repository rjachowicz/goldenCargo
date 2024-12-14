package com.goldencargo.repository;

import com.goldencargo.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.isDeleted = false")
    List<User> findByIsDeletedFalse();

    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.email = :email")
    User findByEmailAndIsDeletedFalse(String email);

    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.userId NOT IN (SELECT d.user.userId FROM Driver d)")
    List<User> findUsersNotAssignedAsDrivers();

    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.userId NOT IN (SELECT l.user.userId FROM Logistics l)")
    List<User> findUsersNotAssignedAsLogistic();

    @Modifying
    @Transactional
    @Query("UPDATE User b SET b.isDeleted = true WHERE b.userId = :id")
    void softDelete(@Param("id") Long id);

    Optional<User> findByUsername(String username);
}