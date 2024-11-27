package com.goldencargo.repository;

import com.goldencargo.model.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE News b SET b.isDeleted = true WHERE b.newsId = :id")
    void softDelete(@Param("id") Long id);
}