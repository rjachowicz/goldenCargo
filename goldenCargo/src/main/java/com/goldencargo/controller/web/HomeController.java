package com.goldencargo.controller.web;

import com.goldencargo.service.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String getDashboardData(Model model) {
        model.addAttribute("userRolesLabels", homeService.getUserRoleLabels());
        model.addAttribute("userRolesData", homeService.getUserRoleData());

        model.addAttribute("orderStatusesLabels", homeService.getOrderStatusLabels());
        model.addAttribute("orderStatusesData", homeService.getOrderStatusData());

        model.addAttribute("vehicleStatusesLabels", homeService.getVehicleStatusLabels());
        model.addAttribute("vehicleStatusesData", homeService.getVehicleStatusData());

        model.addAttribute("messageStatusesLabels", homeService.getMessageStatusLabels());
        model.addAttribute("messageStatusesData", homeService.getMessageStatusData());

        model.addAttribute("incidentTypesLabels", homeService.getIncidentTypeLabels());
        model.addAttribute("incidentTypesData", homeService.getIncidentTypeData());

        model.addAttribute("vehicleRepairLabels", homeService.getVehicleRepairLabels());
        model.addAttribute("vehicleRepairData", homeService.getVehicleRepairData());

        model.addAttribute("transportStatusLabels", homeService.getTransportStatusLabels());
        model.addAttribute("transportStatusData", homeService.getTransportStatusData());

        model.addAttribute("clientInvoiceStatusLabels", homeService.getClientInvoiceStatusLabels());
        model.addAttribute("clientInvoiceStatusData", homeService.getClientInvoiceStatusData());

        return "home";
    }
}
