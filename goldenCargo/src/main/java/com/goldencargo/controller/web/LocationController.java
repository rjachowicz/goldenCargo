package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Location;
import com.goldencargo.model.entities.Route;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;
    private final GenericService genericService;
    private static final String ALIAS = "l";


    public LocationController(LocationService locationService, GenericService genericService) {
        this.locationService = locationService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllLocations(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {
        List<Location> locations = genericService.getFilteredAndSortedEntities(
                Location.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );
        model.addAttribute("locations", locations);
        model.addAttribute("location", new Location());
        return "locations/main";
    }

    @PostMapping("/create")
    public String createLocation(@ModelAttribute Location location) {
        locationService.createLocation(location);
        return "redirect:/locations";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Location location = locationService.getLocationById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid location ID: " + id));
        model.addAttribute("location", location);
        return "locations/edit :: editLocationModal";
    }

    @PostMapping("/update/{id}")
    public String updateLocation(@PathVariable Long id, @ModelAttribute Location locationDetails) {
        locationService.updateLocation(id, locationDetails);
        return "redirect:/locations";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Location location = locationService.getLocationById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid location ID: " + id));
        model.addAttribute("location", location);
        return "locations/details :: detailsLocationModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        if (locationService.deleteLocation(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
