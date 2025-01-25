package com.goldencargo.controller.web;

import com.goldencargo.model.entities.ClientInvoice;
import com.goldencargo.service.ClientInvoiceService;
import com.goldencargo.service.ClientOrderService;
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
@RequestMapping("/client-invoices")
public class ClientInvoiceController {

    private final ClientInvoiceService clientInvoiceService;
    private final ClientService clientService;
    private final ClientOrderService clientOrderService;
    private final GenericService genericService;

    private static final String ALIAS = "ci";

    public ClientInvoiceController(ClientInvoiceService clientInvoiceService,
                                   ClientService clientService,
                                   ClientOrderService clientOrderService,
                                   GenericService genericService) {
        this.clientInvoiceService = clientInvoiceService;
        this.clientService = clientService;
        this.clientOrderService = clientOrderService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllClientInvoices(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "dateIssued") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<ClientInvoice> clientInvoices = genericService.getFilteredAndSortedEntities(
                ClientInvoice.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("clientInvoices", clientInvoices);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortLogic", sortLogic);
        model.addAttribute("clientInvoice", new ClientInvoice());
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("clientOrders", clientOrderService.getAllClientOrders());
        return "client-invoices/main";
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
            return "client-invoices/edit :: editClientInvoiceModal";
        }
        return "redirect:/client-invoices";
    }

    @PostMapping("/update/{id}")
    public String updateClientInvoice(@PathVariable Long id,
                                      @ModelAttribute ClientInvoice clientInvoiceDetails) {
        clientInvoiceService.updateClientInvoice(id, clientInvoiceDetails);
        return "redirect:/client-invoices";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<ClientInvoice> clientInvoice = clientInvoiceService.getClientInvoiceById(id);
        if (clientInvoice.isPresent()) {
            model.addAttribute("clientInvoice", clientInvoice.get());
            return "client-invoices/details :: detailsClientInvoiceModal";
        }
        return "redirect:/client-invoices";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClientInvoice(@PathVariable Long id) {
        boolean isDeleted = clientInvoiceService.deleteClientInvoice(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
