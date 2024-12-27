package com.goldencargo.controller.web;

import com.goldencargo.model.data.PaymentStatus;
import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.ClientOrder;
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
@RequestMapping("/client-orders")
public class ClientOrderController {

    private static final String ALIAS = "co";
    private final ClientOrderService clientOrderService;
    private final ClientService clientService;
    private final GenericService genericService;

    public ClientOrderController(ClientOrderService clientOrderService, ClientService clientService, GenericService genericService) {
        this.clientOrderService = clientOrderService;
        this.clientService = clientService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllClientOrders(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "orderDate") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<ClientOrder> clientOrders = genericService.getFilteredAndSortedEntities(
                ClientOrder.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );
        model.addAttribute("clientOrders", clientOrders);
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortLogic", sortLogic);
        model.addAttribute("clientOrder", new ClientOrder());
        return "client-orders/main";
    }

    @PostMapping("/create")
    public String createClientOrder(@ModelAttribute ClientOrder clientOrder) {
        clientOrderService.createClientOrder(clientOrder);
        return "redirect:/client-orders";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<ClientOrder> clientOrder = clientOrderService.getClientOrderById(id);
        if (clientOrder.isPresent()) {
            model.addAttribute("clientOrder", clientOrder.get());
            model.addAttribute("clients", clientService.getAllClients());
            model.addAttribute("statuses", Status.values());
            model.addAttribute("paymentStatuses", PaymentStatus.values());
            return "client-orders/edit :: editClientOrderModal";
        }
        return "redirect:/client-orders";
    }

    @PostMapping("/update/{id}")
    public String updateClientOrder(@PathVariable Long id, @ModelAttribute ClientOrder clientOrderDetails) {
        clientOrderService.updateClientOrder(id, clientOrderDetails);
        return "redirect:/client-orders";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<ClientOrder> clientOrder = clientOrderService.getClientOrderByIdWithDetails(id);
        if (clientOrder.isPresent()) {
            model.addAttribute("clientOrder", clientOrder.get());
            return "client-orders/details :: detailsClientOrderModal";
        }
        return "redirect:/client-orders";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClientOrder(@PathVariable Long id) {
        boolean isDeleted = clientOrderService.deleteClientOrder(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
