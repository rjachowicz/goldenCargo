package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Client;
import com.goldencargo.service.ClientService;
import com.goldencargo.service.GenericService;
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

    private static final String ALIAS = "c";
    private final ClientService clientService;
    private final GenericService genericService;

    public ClientController(ClientService clientService, GenericService genericService) {
        this.clientService = clientService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllClients(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<Client> clients = genericService.getFilteredAndSortedEntities(
                Client.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );
        model.addAttribute("clients", clients);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortLogic", sortLogic);
        model.addAttribute("client", new Client());
        return "clients/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new Client());
        return "clients/create";
    }

    @PostMapping("/create")
    public String createClient(@ModelAttribute Client client) {
        clientService.createClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Client> client = clientService.getClientById(id);
        if (client.isPresent()) {
            model.addAttribute("client", client.get());
            return "clients/edit :: editClientModal";
        }
        return "redirect:/clients";
    }

    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable Long id, @ModelAttribute Client clientDetails) {
        clientService.updateClient(id, clientDetails);
        return "redirect:/clients";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Client> client = clientService.getClientById(id);
        if (client.isPresent()) {
            model.addAttribute("client", client.get());
            return "clients/details :: detailsClientModal";
        }
        return "redirect:/clients";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        boolean isDeleted = clientService.deleteClient(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
