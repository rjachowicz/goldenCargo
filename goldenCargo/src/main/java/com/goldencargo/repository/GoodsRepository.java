package com.goldencargo.repository;

import com.goldencargo.model.entities.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    List<Goods> findByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Goods b SET b.isDeleted = true WHERE b.goodsId = :id")
    void softDelete(@Param("id") Long id);
}