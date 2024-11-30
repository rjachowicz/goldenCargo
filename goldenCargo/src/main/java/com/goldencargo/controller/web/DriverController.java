package com.goldencargo.controller.web;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.Driver;
import com.goldencargo.service.DriverService;
import com.goldencargo.service.GenericService;
import com.goldencargo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;
    private final UserService userService;
    private final GenericService genericService;

    private static final String ALIAS = "d";

    public DriverController(DriverService driverService, UserService userService, GenericService genericService) {
        this.driverService = driverService;
        this.userService = userService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllDrivers(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "licenseNumber") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<Driver> drivers = genericService.getFilteredAndSortedEntities(
                Driver.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );

        model.addAttribute("drivers", drivers);
        model.addAttribute("driver", new Driver());
        model.addAttribute("users", userService.getUsersNotAssignedAsDrivers());
        model.addAttribute("statuses", Status.values());

        return "drivers/main";
    }

    @PostMapping("/create")
    public String createDriver(@ModelAttribute Driver driver) {
        driverService.createDriver(driver);
        return "redirect:/drivers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Driver> driver = driverService.getDriverById(id);
        if (driver.isPresent()) {
            model.addAttribute("driver", driver.get());
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("statuses", Status.values());
            return "drivers/edit :: editDriverModal";
        }
        return "redirect:/drivers";
    }

    @PostMapping("/update/{id}")
    public String updateDriver(@PathVariable Long id, @ModelAttribute Driver driverDetails) {
        driverService.updateDriver(id, driverDetails);
        return "redirect:/drivers";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Driver> driver = driverService.getDriverById(id);
        driver.ifPresent(value -> model.addAttribute("driver", value));
        return "drivers/details :: detailsDriverModal";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        boolean isDeleted = driverService.deleteDriver(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
