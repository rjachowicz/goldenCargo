package com.goldencargo.repository;

import com.goldencargo.model.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Message b SET b.isDeleted = true WHERE b.messageId = :id")
    void softDelete(@Param("id") Long id);
}