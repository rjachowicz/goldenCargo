package com.goldencargo.controller.web;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.Transport;
import com.goldencargo.service.TransportOrderService;
import com.goldencargo.service.TransportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/transports")
public class TransportController {

    private final TransportService transportService;
    private final TransportOrderService transportOrderService;

    public TransportController(TransportService transportService, TransportOrderService transportOrderService) {
        this.transportService = transportService;
        this.transportOrderService = transportOrderService;
    }

    @GetMapping
    public String getAllTransports(Model model) {
        List<Transport> transports = transportService.getAllTransports();
        model.addAttribute("transports", transports);
        return "transports/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("transport", new Transport());
        model.addAttribute("transportOrders", transportOrderService.getTransportOrdersNotAssignedToTransport());
        model.addAttribute("statuses", Status.values());
        return "transports/create";
    }

    @PostMapping("/create")
    public String createTransport(Transport transport) {
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
            return "transports/edit";
        }
        return "redirect:/transports";
    }

    @PostMapping("/update/{id}")
    public String updateTransport(@PathVariable Long id, Transport transportDetails) {
        transportService.updateTransport(id, transportDetails);
        return "redirect:/transports";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Transport> transport = transportService.getTransportById(id);
        transport.ifPresent(value -> model.addAttribute("transport", value));
        return "transports/details";
    }

    @PostMapping("/delete/{id}")
    public String deleteTransport(@PathVariable Long id) {
        transportService.deleteTransport(id);
        return "redirect:/transports";
    }
}
