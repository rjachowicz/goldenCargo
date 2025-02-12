package com.goldencargo.controller.api;

import com.goldencargo.config.FcmService;
import com.goldencargo.model.entities.FcmToken;
import com.goldencargo.model.entities.User;
import com.goldencargo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    private final FcmService fcmService;
    private final UserService userService;

    public ApiUserController(FcmService fcmService, UserService userService) {
        this.fcmService = fcmService;
        this.userService = userService;
    }

    @PostMapping(value = "/registerToken", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerFcmToken(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody Map<String, String> request) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        String fcmToken = request.get("fcmToken");
        if (fcmToken == null || fcmToken.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("FCM token is required");
        }

        User user = userService.findUserByUsername(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        try {
            FcmToken savedToken = fcmService.saveFcmTokenForUser(user.getUserId(), fcmToken);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving FCM token");
        }
    }

}
