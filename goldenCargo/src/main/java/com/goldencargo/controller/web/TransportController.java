package com.goldencargo.controller.web;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.Transport;
import com.goldencargo.model.entities.TransportOrder;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.TransportOrderService;
import com.goldencargo.service.TransportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/transports")
public class TransportController {

    private static final String ALIAS = "t";

    private final TransportService transportService;
    private final TransportOrderService transportOrderService;
    private final GenericService genericService;

    public TransportController(TransportService transportService,
                               TransportOrderService transportOrderService,
                               GenericService genericService) {
        this.transportService = transportService;
        this.transportOrderService = transportOrderService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllTransports(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "transportOrder.name") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<Transport> transports = genericService.getFilteredAndSortedEntities(
                Transport.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("transports", transports);
        model.addAttribute("transport", new Transport());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("transportOrders", transportOrderService.getTransportOrdersNotAssignedToTransport());
        return "transports/main";
    }

    @PostMapping("/create")
    public String createTransport(@ModelAttribute Transport transport) {
        transportService.createTransport(transport);
        return "redirect:/transports";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Transport> transport = transportService.getTransportById(id);
        if (transport.isPresent()) {
            model.addAttribute("transport", transport.get());
            model.addAttribute("transportOrders", transportOrderService.getAllOrders());
            model.addAttribute("statuses", Status.values());
            return "transports/edit :: editTransportModal";
        }
        return "redirect:/transports";
    }

    @PostMapping("/update/{id}")
    public String updateTransport(@PathVariable Long id, @ModelAttribute Transport transportDetails) {
        transportService.updateTransport(id, transportDetails);
        return "redirect:/transports";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Transport> transport = transportService.getTransportById(id);
        transport.ifPresent(value -> model.addAttribute("transport", value));
        return "transports/details :: detailsTransportModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTransport(@PathVariable Long id) {
        boolean isDeleted = transportService.deleteTransport(id);
        return isDeleted ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(value = "/new-transport", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createTransport(@RequestParam(value = "transportOrderId", required = false) Long transportOrderId,
                                  @ModelAttribute Transport transport) {
        if (transportOrderId == null) {
            throw new RuntimeException("Transport Order ID is missing!");
        }
        TransportOrder transportOrder = transportOrderService.getOrderById(transportOrderId)
                .orElseThrow(() -> new RuntimeException("Transport Order not found"));

        transportOrder.setStatus(Status.COMPLETED);
        transport.setTransportOrder(transportOrder);
        transportOrderService.updateOrder(transportOrder.getTransportOrderId(), transportOrder);

        transportService.createTransport(transport);

        return "redirect:/transport-orders/new-transport-order";
    }

}
