package com.goldencargo.controller;

import com.goldencargo.model.entities.Goods;
import com.goldencargo.service.GoodsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Goods>> getAllGoods() {
        List<Goods> goodsList = goodsService.getAllGoods();
        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Goods> getGoodsById(@PathVariable Long id) {
        Optional<Goods> goods = goodsService.getGoodsById(id);
        return goods.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Goods> createGoods(@RequestBody Goods goods) {
        Goods createdGoods = goodsService.createGoods(goods);
        return new ResponseEntity<>(createdGoods, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Goods> updateGoods(@PathVariable Long id, @RequestBody Goods goodsDetails) {
        Optional<Goods> updatedGoods = goodsService.updateGoods(id, goodsDetails);
        return updatedGoods.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable Long id) {
        boolean isDeleted = goodsService.deleteGoods(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}