package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Goods;
import com.goldencargo.service.ClientOrderService;
import com.goldencargo.service.GoodsService;
import com.goldencargo.service.GenericService;
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

    private static final String ALIAS = "g";
    private final GoodsService goodsService;
    private final ClientOrderService clientOrderService;
    private final GenericService genericService;

    public GoodsController(GoodsService goodsService, ClientOrderService clientOrderService, GenericService genericService) {
        this.goodsService = goodsService;
        this.clientOrderService = clientOrderService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllGoods(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<Goods> goodsList = genericService.getFilteredAndSortedEntities(
                Goods.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("goodsList", goodsList);
        model.addAttribute("goods", new Goods());
        model.addAttribute("clientOrders", clientOrderService.getAllClientOrders());

        return "goods/main";
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
            return "goods/edit :: editGoodsModal";
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
        goods.ifPresent(value -> model.addAttribute("goods", value));
        return "goods/details :: detailsGoodsModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable Long id) {
        boolean isDeleted = goodsService.deleteGoods(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
