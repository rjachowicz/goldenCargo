package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Goods;
import com.goldencargo.service.ClientOrderService;
import com.goldencargo.service.GoodsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;
    private final ClientOrderService clientOrderService;

    public GoodsController(GoodsService goodsService, ClientOrderService clientOrderService) {
        this.goodsService = goodsService;
        this.clientOrderService = clientOrderService;
    }

    @GetMapping
    public String getAllGoods(Model model) {
        List<Goods> goodsList = goodsService.getAllGoods();
        model.addAttribute("goodsList", goodsList);
        return "goods/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("goods", new Goods());
        model.addAttribute("clientOrders", clientOrderService.getAllClientOrders());
        return "goods/create";
    }

    @PostMapping("/create")
    public String createGoods(@ModelAttribute Goods goods) {
        goodsService.createGoods(goods);
        return "redirect:/goods";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Goods> goods = goodsService.getGoodsById(id);
        if (goods.isPresent()) {
            model.addAttribute("goods", goods.get());
            model.addAttribute("clientOrders", clientOrderService.getAllClientOrders());
            return "goods/edit";
        }
        return "redirect:/goods";
    }

    @PostMapping("/update/{id}")
    public String updateGoods(@PathVariable Long id, @ModelAttribute Goods goodsDetails) {
        goodsService.updateGoods(id, goodsDetails);
        return "redirect:/goods";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Goods> goods = goodsService.getGoodsById(id);
        if (goods.isPresent()) {
            model.addAttribute("goods", goods.get());
            return "goods/details";
        }
        return "redirect:/goods";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable Long id) {
        return goodsService.deleteGoods(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
