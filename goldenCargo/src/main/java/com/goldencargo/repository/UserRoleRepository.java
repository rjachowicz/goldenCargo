package com.goldencargo.repository;

import com.goldencargo.model.data.UserRoleId;
import com.goldencargo.model.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}