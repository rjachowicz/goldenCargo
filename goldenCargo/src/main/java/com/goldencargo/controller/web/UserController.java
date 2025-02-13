package com.goldencargo.controller.web;

import com.goldencargo.model.entities.User;
import com.goldencargo.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final String ALIAS = "u";

    private final UserService userService;
    private final RoleService roleService;
    private final GenericService genericService;
    private final EmailService emailService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final UserRoleService userRoleService;
    private static final String SEARCH_VALUE = "like";

    private final static String URL = "http://localhost:8080/users/reset-password?token=";


    public UserController(UserService userService, RoleService roleService,
                          GenericService genericService,
                          EmailService emailService,
                          PasswordResetTokenService passwordResetTokenService, UserRoleService userRoleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.genericService = genericService;
        this.emailService = emailService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.userRoleService = userRoleService;
    }

    @GetMapping
    public String getAllUsers(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "sortBy", required = false, defaultValue = "username") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {
        List<User> users = genericService.getFilteredAndSortedEntities(
                User.class,
                ALIAS,
                filterType,
                filterValue,
                SEARCH_VALUE,
                sortBy,
                sortLogic
        );
        model.addAttribute("users", users);
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("statuses", User.UserStatus.values());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortLogic", sortLogic);
        model.addAttribute("user", new User());
        return "users/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "users/create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user, @RequestParam("roleId") Long roleId) {
        User createdUser = userService.createUser(user);
        userService.assignRoleToUser(createdUser, roleId);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("statuses", User.UserStatus.values());
            model.addAttribute("roles", roleService.getAllRoles());
            model.addAttribute("userRoleId", userRoleService.getCurrentRoleId(user.get()));
            return "users/edit :: editUserModal";
        }
        return "redirect:/users";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute User userDetails,
                             @RequestParam(value = "newPassword", required = false) String newPassword,
                             @RequestParam("roleId") Long roleId) {
        userService.updateUser(id, userDetails, newPassword);
        userService.updateUserRole(id, roleId);
        return "redirect:/users";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "users/details :: detailsUserModal";
        }
        return "redirect:/users";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "users/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email, Model model) {
        User user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("errorMessage", "No account found with that email.");
            return "users/forgot-password";
        }

        String token = passwordResetTokenService.createTokenForUser(user);

        emailService.sendSimpleEmail(email, "Password Reset Request",
                "Click the link to reset your password: " + URL + token);

        model.addAttribute("successMessage", "A password reset link has been sent to your email.");
        return "users/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        Optional<User> user = passwordResetTokenService.getUserByToken(token);
        if (user.isEmpty()) {
            model.addAttribute("invalidToken", true);
            model.addAttribute("errorMessage", "Invalid or expired token.");
            return "users/reset-password";
        }
        model.addAttribute("token", token);
        model.addAttribute("email", user.get().getEmail());
        model.addAttribute("invalidToken", false);
        return "users/reset-password";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(
            @RequestParam("token") String token,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match.");
            model.addAttribute("token", token);
            return "users/reset-password";
        }

        Optional<User> user = passwordResetTokenService.getUserByToken(token);
        if (user.isEmpty()) {
            model.addAttribute("errorMessage", "Invalid or expired token.");
            return "users/reset-password";
        }

        userService.updateUserPassword(user.get(), password);
        passwordResetTokenService.invalidateToken(token);

        model.addAttribute("successMessage", "Your password has been changed successfully.");
        return "users/reset-password";
    }
}
