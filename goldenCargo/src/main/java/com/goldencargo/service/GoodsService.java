package com.goldencargo.service;

import com.goldencargo.model.entities.Goods;
import com.goldencargo.repository.GoodsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public GoodsService(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    public List<Goods> getAllGoods() {
        return goodsRepository.findByIsDeletedFalse();
    }

    public List<Goods> getGoodsByIds(List<Long> ids) {
        return goodsRepository.findAllById(ids);
    }

    public Optional<Goods> getGoodsById(Long id) {
        return goodsRepository.findById(id);
    }

    public Goods createGoods(Goods goods) {
        return goodsRepository.save(goods);
    }

    public Optional<Goods> updateGoods(Long id, Goods goodsDetails) {
        return goodsRepository.findById(id).map(goods -> {
            goods.setName(goodsDetails.getName());
            goods.setDescription(goodsDetails.getDescription());
            goods.setWeight(goodsDetails.getWeight());
            goods.setDimensions(goodsDetails.getDimensions());
            goods.setSpecialHandlingInstructions(goodsDetails.getSpecialHandlingInstructions());
            goods.setUpdatedAt(new java.util.Date());
            return goodsRepository.save(goods);
        });
    }

    public boolean deleteGoods(Long id) {
        if (goodsRepository.existsById(id)) {
            goodsRepository.softDelete(id);
            return true;
        }
        return false;
    }

    public double calculateTotalAmount(Set<Goods> goods) {
        double sum = 0;
        for (Goods good : goods) {
            sum += good.getWeight();
        }
        return sum;
    }
}