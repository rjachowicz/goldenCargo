package com.goldencargo.controller.web;

import com.goldencargo.model.entities.ClientInvoice;
import com.goldencargo.service.ClientInvoiceService;
import com.goldencargo.service.ClientOrderService;
import com.goldencargo.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientInvoices")
public class ClientInvoiceController {

    private final ClientInvoiceService clientInvoiceService;
    private final ClientService clientService;
    private final ClientOrderService clientOrderService;

    public ClientInvoiceController(ClientInvoiceService clientInvoiceService, ClientService clientService, ClientOrderService clientOrderService) {
        this.clientInvoiceService = clientInvoiceService;
        this.clientService = clientService;
        this.clientOrderService = clientOrderService;
    }

    @GetMapping
    public String getAllClientInvoices(Model model) {
        List<ClientInvoice> clientInvoices = clientInvoiceService.getAllClientInvoices();
        model.addAttribute("clientInvoices", clientInvoices);
        return "clientInvoices/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("clientInvoice", new ClientInvoice());
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("clientOrders", clientOrderService.getAllClientOrders());
        return "clientInvoices/create";
    }

    @PostMapping("/create")
    public String createClientInvoice(@ModelAttribute ClientInvoice clientInvoice) {
        clientInvoiceService.createClientInvoice(clientInvoice);
        return "redirect:/clientInvoices";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<ClientInvoice> clientInvoice = clientInvoiceService.getClientInvoiceById(id);
        if (clientInvoice.isPresent()) {
            model.addAttribute("clientInvoice", clientInvoice.get());
            model.addAttribute("clients", clientService.getAllClients());
            model.addAttribute("clientOrders", clientOrderService.getAllClientOrders());
            return "clientInvoices/edit";
        }
        return "redirect:/clientInvoices";
    }

    @PostMapping("/update/{id}")
    public String updateClientInvoice(@PathVariable Long id, ClientInvoice clientInvoiceDetails) {
        clientInvoiceService.updateClientInvoice(id, clientInvoiceDetails);
        return "redirect:/clientInvoices";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<ClientInvoice> clientInvoice = clientInvoiceService.getClientInvoiceById(id);
        if (clientInvoice.isPresent()) {
            model.addAttribute("clientInvoice", clientInvoice.get());
            return "clientInvoices/details";
        }
        return "redirect:/clientInvoices";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteClientInvoice(@PathVariable Long id) {
        clientInvoiceService.deleteClientInvoice(id);
        return "redirect:/clientInvoices";
    }
}
