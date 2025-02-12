package com.goldencargo.controller.api;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.dto.api.VehicleDTO;
import com.goldencargo.model.entities.Incident;
import com.goldencargo.model.entities.User;
import com.goldencargo.model.entities.Vehicle;
import com.goldencargo.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/incidents")
public class ApiIncidentController {

    private final IncidentService incidentService;
    private final DriverVehicleService driverVehicleService;
    private final UserService userService;

    public ApiIncidentController(IncidentService incidentService,
                                 DriverVehicleService driverVehicleService,
                                 UserService userService) {
        this.incidentService = incidentService;
        this.driverVehicleService = driverVehicleService;
        this.userService = userService;
    }

    @GetMapping("/vehicles")
    public ResponseEntity<?> getVehiclesForUser(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = getUser(userDetails);
            if (user.getDriver() == null) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("error", "User is not a driver."));
            }

            Long driverId = user.getDriver().getDriverId();
            List<VehicleDTO> vehicles = driverVehicleService.getVehiclesForDriver(driverId);

            return ResponseEntity.ok(vehicles);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/incidents")
    public ResponseEntity<?> getMyIncidents(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = getUser(userDetails);
            return ResponseEntity.ok(incidentService.getIncidentsByReportedBy(user.getUserId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createIncident(@AuthenticationPrincipal UserDetails userDetails,
                                                              @RequestBody Incident incident) {
        try {
            User user = getUser(userDetails);

            incident.setDriver(user.getDriver());
            incident.setDate(new Date());
            incident.setReportedBy(user);
            incident.setStatus(Status.NEW);

            incidentService.createIncident(incident);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("message", "Incident has been created"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }


    private User getUser(UserDetails userDetails) {
        String userName = userDetails.getUsername();
        return userService.findUserByUsername(userName);
    }

}
