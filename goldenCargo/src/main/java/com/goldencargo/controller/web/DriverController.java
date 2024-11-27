package com.goldencargo.controller.web;

import com.goldencargo.model.entities.Driver;
import com.goldencargo.service.DriverService;
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

    public DriverController(DriverService driverService, UserService userService) {
        this.driverService = driverService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllDrivers(Model model) {
        List<Driver> drivers = driverService.getAllDrivers();
        model.addAttribute("drivers", drivers);
        return "drivers/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("driver", new Driver());
        model.addAttribute("users", userService.getUsersNotAssignedAsDrivers());
        return "drivers/create";
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
            return "drivers/edit";
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
        if (driver.isPresent()) {
            model.addAttribute("driver", driver.get());
            return "drivers/details";
        }
        return "redirect:/drivers";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        return driverService.deleteDriver(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
