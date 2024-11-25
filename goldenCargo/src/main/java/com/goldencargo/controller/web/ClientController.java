package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Client;
import com.goldencargo.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String getAllClients(Model model) {
        List<Client> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "clients/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("formTitle", "Create New Client");
        return "clients/create";
    }

    @PostMapping("/create")
    public String createClient(Client client) {
        clientService.createClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Client> client = clientService.getClientById(id);
        if (client.isPresent()) {
            model.addAttribute("client", client.get());
            model.addAttribute("formTitle", "Edit Client");
            return "clients/edit";
        }
        return "redirect:/clients";
    }

    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable Long id, Client clientDetails) {
        clientService.updateClient(id, clientDetails);
        return "redirect:/clients";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Client> client = clientService.getClientById(id);
        if (client.isPresent()) {
            model.addAttribute("client", client.get());
            return "clients/details";
        }
        return "redirect:/clients";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        boolean isDeleted = clientService.deleteClient(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
