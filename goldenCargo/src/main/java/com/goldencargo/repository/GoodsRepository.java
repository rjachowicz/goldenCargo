package com.goldencargo.repository;

import com.goldencargo.model.entities.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
}