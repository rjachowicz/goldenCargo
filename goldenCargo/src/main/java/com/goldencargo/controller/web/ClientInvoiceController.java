package com.goldencargo.controller.web;

import com.goldencargo.model.entities.ClientInvoice;
import com.goldencargo.service.ClientInvoiceService;
import com.goldencargo.service.ClientOrderService;
import com.goldencargo.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/client-invoices")
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
        return "client-invoices/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("clientInvoice", new ClientInvoice());
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("clientOrders", clientOrderService.getAllClientOrders());
        return "client-invoices/create";
    }

    @PostMapping("/create")
    public String createClientInvoice(@ModelAttribute ClientInvoice clientInvoice) {
        clientInvoiceService.createClientInvoice(clientInvoice);
        return "redirect:/client-invoices";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<ClientInvoice> clientInvoice = clientInvoiceService.getClientInvoiceById(id);
        if (clientInvoice.isPresent()) {
            model.addAttribute("clientInvoice", clientInvoice.get());
            model.addAttribute("clients", clientService.getAllClients());
            model.addAttribute("clientOrders", clientOrderService.getAllClientOrders());
            return "client-invoices/edit";
        }
        return "redirect:/client-invoices";
    }

    @PostMapping("/update/{id}")
    public String updateClientInvoice(@PathVariable Long id, ClientInvoice clientInvoiceDetails) {
        clientInvoiceService.updateClientInvoice(id, clientInvoiceDetails);
        return "redirect:/client-invoices";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<ClientInvoice> clientInvoice = clientInvoiceService.getClientInvoiceById(id);
        if (clientInvoice.isPresent()) {
            model.addAttribute("clientInvoice", clientInvoice.get());
            return "client-invoices/details";
        }
        return "redirect:/client-invoices";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClientInvoice(@PathVariable Long id) {
        return clientInvoiceService.deleteClientInvoice(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
