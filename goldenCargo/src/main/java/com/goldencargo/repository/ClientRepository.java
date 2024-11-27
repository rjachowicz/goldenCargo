package com.goldencargo.repository;

import com.goldencargo.model.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Client b SET b.isDeleted = true WHERE b.clientId = :id")
    void softDelete(@Param("id") Long id);
}
