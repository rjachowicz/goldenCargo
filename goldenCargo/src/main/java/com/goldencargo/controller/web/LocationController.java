package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Location;
import com.goldencargo.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public String getAllLocations(Model model) {
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("location", new Location());
        return "locations/create";
    }

    @PostMapping("/create")
    public String createLocation(Location location) {
        locationService.createLocation(location);
        return "redirect:/locations";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Location> location = locationService.getLocationById(id);
        location.ifPresent(value -> model.addAttribute("location", value));
        return location.isPresent() ? "locations/edit" : "redirect:/locations";
    }

    @PostMapping("/update/{id}")
    public String updateLocation(@PathVariable Long id, Location locationDetails) {
        locationService.updateLocation(id, locationDetails);
        return "redirect:/locations";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Location> location = locationService.getLocationById(id);
        location.ifPresent(value -> model.addAttribute("location", value));
        return "locations/details";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        return locationService.deleteLocation(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
