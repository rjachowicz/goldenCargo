package com.goldencargo.controller.web;

import com.goldencargo.model.entities.News;
import com.goldencargo.service.HomeService;
import com.goldencargo.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final HomeService homeService;
    private final NewsService newsService;

    public HomeController(HomeService homeService, NewsService newsService) {
        this.homeService = homeService;
        this.newsService = newsService;
    }

    @GetMapping("/")
    public String getDashboardData(Model model) {
        List<News> newsList = newsService.getAllNews();

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

        model.addAttribute("newsList", newsList);

        return "home";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("message", "You do not have permission to access this resource.");
        return "/helpers/access-denied";
    }

}
