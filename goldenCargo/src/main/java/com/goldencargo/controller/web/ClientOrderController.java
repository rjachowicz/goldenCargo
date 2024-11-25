package com.goldencargo.controller.web;

import com.goldencargo.model.entities.ClientOrder;
import com.goldencargo.model.entities.Client;
import com.goldencargo.model.data.Status;
import com.goldencargo.model.data.PaymentStatus;
import com.goldencargo.service.ClientOrderService;
import com.goldencargo.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientOrders")
public class ClientOrderController {

    private final ClientOrderService clientOrderService;
    private final ClientService clientService;

    public ClientOrderController(ClientOrderService clientOrderService, ClientService clientService) {
        this.clientOrderService = clientOrderService;
        this.clientService = clientService;
    }

    @GetMapping
    public String getAllClientOrders(Model model) {
        List<ClientOrder> clientOrders = clientOrderService.getAllClientOrders();
        model.addAttribute("clientOrders", clientOrders);
        return "clientOrders/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("clientOrder", new ClientOrder());
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("paymentStatuses", PaymentStatus.values());
        return "clientOrders/create";
    }

    @PostMapping("/create")
    public String createClientOrder(@ModelAttribute ClientOrder clientOrder) {
        clientOrderService.createClientOrder(clientOrder);
        return "redirect:/clientOrders";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<ClientOrder> clientOrder = clientOrderService.getClientOrderById(id);
        if (clientOrder.isPresent()) {
            model.addAttribute("clientOrder", clientOrder.get());
            model.addAttribute("clients", clientService.getAllClients());
            model.addAttribute("statuses", Status.values());
            model.addAttribute("paymentStatuses", PaymentStatus.values());
            return "clientOrders/edit";
        }
        return "redirect:/clientOrders";
    }

    @PostMapping("/update/{id}")
    public String updateClientOrder(@PathVariable Long id, @ModelAttribute ClientOrder clientOrderDetails) {
        clientOrderService.updateClientOrder(id, clientOrderDetails);
        return "redirect:/clientOrders";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<ClientOrder> clientOrder = clientOrderService.getClientOrderById(id);
        if (clientOrder.isPresent()) {
            model.addAttribute("clientOrder", clientOrder.get());
            return "clientOrders/details";
        }
        return "redirect:/clientOrders";
    }

    @PostMapping("/delete/{id}")
    public String deleteClientOrder(@PathVariable Long id) {
        clientOrderService.deleteClientOrder(id);
        return "redirect:/clientOrders";
    }
}
