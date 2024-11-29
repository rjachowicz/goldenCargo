package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Location;
import com.goldencargo.model.entities.Route;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.LocationService;
import com.goldencargo.service.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;
    private final LocationService locationService;
    private final GenericService genericService;

    private static final String ALIAS = "r";

    public RouteController(RouteService routeService, LocationService locationService, GenericService genericService) {
        this.routeService = routeService;
        this.locationService = locationService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllRoutes(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "startLocation.name") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<Route> routes = genericService.getFilteredAndSortedEntities(
                Route.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        List<Location> allLocations = locationService.getAllLocations();

        model.addAttribute("routeList", routes);
        model.addAttribute("route", new Route());
        model.addAttribute("locations", allLocations);
        return "routes/main";
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
            return "routes/edit :: editRouteModal";
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
        return "routes/details :: detailsRouteModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
