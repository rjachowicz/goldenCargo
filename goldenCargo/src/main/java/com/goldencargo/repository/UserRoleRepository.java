package com.goldencargo.repository;

import com.goldencargo.model.data.authData.UserRoleId;
import com.goldencargo.model.entities.User;
import com.goldencargo.model.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    @Modifying
    @Transactional
    @Query("DELETE FROM UserRole ur WHERE ur.user = :user")
    void deleteByUser(@Param("user") User user);

    Optional<UserRole> findByUser(User user);

}