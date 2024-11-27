package com.goldencargo.repository;

import com.goldencargo.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Role b SET b.isDeleted = true WHERE b.roleId = :id")
    void softDelete(@Param("id") Long id);
}