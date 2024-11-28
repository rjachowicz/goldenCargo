package com.goldencargo.repository;

import com.goldencargo.model.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Notification b SET b.isDeleted = true WHERE b.notificationId = :id")
    void softDelete(@Param("id") Long id);
}