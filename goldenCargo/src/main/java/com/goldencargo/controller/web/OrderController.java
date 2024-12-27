package com.goldencargo.controller.web;

import com.goldencargo.model.data.InvoiceType;
import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.Order;
import com.goldencargo.service.ClientOrderService;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.OrderService;
import com.goldencargo.service.TransportOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private static final String ALIAS = "o";
    private final OrderService orderService;
    private final ClientOrderService clientOrderService;
    private final TransportOrderService transportOrderService;
    private final GenericService genericService;

    public OrderController(OrderService orderService,
                           ClientOrderService clientOrderService,
                           TransportOrderService transportOrderService,
                           GenericService genericService) {
        this.orderService = orderService;
        this.clientOrderService = clientOrderService;
        this.transportOrderService = transportOrderService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllOrders(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "orderType") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<Order> orders = genericService.getFilteredAndSortedEntities(
                Order.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );
        model.addAttribute("order", new Order());
        model.addAttribute("clientOrders", clientOrderService.getAllClientOrders());
        model.addAttribute("transportOrders", transportOrderService.getAllOrders());
        model.addAttribute("orderTypes", InvoiceType.values());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("orders", orders);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortLogic", sortLogic);
        model.addAttribute("filterType", filterType);
        model.addAttribute("filterValue", filterValue);
        return "orders/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("clientOrders", clientOrderService.getAllClientOrders());
        model.addAttribute("transportOrders", transportOrderService.getAllOrders());
        model.addAttribute("orderTypes", InvoiceType.values());
        model.addAttribute("statuses", Status.values());
        return "orders/create";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order) {
        orderService.createOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            model.addAttribute("clientOrders", clientOrderService.getAllClientOrders());
            model.addAttribute("transportOrders", transportOrderService.getAllOrders());
            model.addAttribute("orderTypes", InvoiceType.values());
            model.addAttribute("statuses", Status.values());
            return "orders/edit";
        }
        return "redirect:/orders";
    }

    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute Order orderDetails) {
        orderService.updateOrder(id, orderDetails);
        return "redirect:/orders";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            return "orders/details";
        }
        return "redirect:/orders";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        return orderService.deleteOrder(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
