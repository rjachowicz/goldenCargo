package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Route;
import com.goldencargo.service.LocationService;
import com.goldencargo.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;
    private final LocationService locationService;

    public RouteController(RouteService routeService, LocationService locationService) {
        this.routeService = routeService;
        this.locationService = locationService;
    }

    @GetMapping
    public String getAllRoutes(Model model) {
        List<Route> routes = routeService.getAllRoutes();
        model.addAttribute("routes", routes);
        return "routes/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("route", new Route());
        model.addAttribute("locations", locationService.getAllLocations());
        return "routes/create";
    }

    @PostMapping("/create")
    public String createRoute(Route route) {
        routeService.createRoute(route);
        return "redirect:/routes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Route> route = routeService.getRouteById(id);
        if (route.isPresent()) {
            model.addAttribute("route", route.get());
            model.addAttribute("locations", locationService.getAllLocations());
            return "routes/edit";
        }
        return "redirect:/routes";
    }

    @PostMapping("/update/{id}")
    public String updateRoute(@PathVariable Long id, Route routeDetails) {
        routeService.updateRoute(id, routeDetails);
        return "redirect:/routes";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Route> route = routeService.getRouteById(id);
        route.ifPresent(value -> model.addAttribute("route", value));
        return "routes/details";
    }

    @PostMapping("/delete/{id}")
    public String deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return "redirect:/routes";
    }
}
