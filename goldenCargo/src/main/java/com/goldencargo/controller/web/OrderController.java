package com.goldencargo.controller.web;

import com.goldencargo.model.data.InvoiceType;
import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.Order;
import com.goldencargo.service.ClientOrderService;
import com.goldencargo.service.OrderService;
import com.goldencargo.service.TransportOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ClientOrderService clientOrderService;
    private final TransportOrderService transportOrderService;

    public OrderController(OrderService orderService,
                           ClientOrderService clientOrderService,
                           TransportOrderService transportOrderService) {
        this.orderService = orderService;
        this.clientOrderService = clientOrderService;
        this.transportOrderService = transportOrderService;
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
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
    public String createOrder(Order order) {
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
    public String updateOrder(@PathVariable Long id, Order orderDetails) {
        orderService.updateOrder(id, orderDetails);
        return "redirect:/orders";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Order> order = orderService.getOrderById(id);
        order.ifPresent(value -> model.addAttribute("order", value));
        return "orders/details";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
