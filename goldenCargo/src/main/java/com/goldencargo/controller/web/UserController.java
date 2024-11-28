package com.goldencargo.controller.web;

import com.goldencargo.model.entities.User;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final GenericService genericService;

    public UserController(UserService userService, GenericService genericService) {
        this.userService = userService;
        this.genericService = genericService;
    }

    @GetMapping
    public String getAllUsers(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false) String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "username") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {
        List<User> users = genericService.getFilteredAndSortedEntities(
                User.class,
                "u",
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );
        model.addAttribute("users", users);
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
    public String createUser(User user) {
        userService.createUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("statuses", User.UserStatus.values());
            return "users/edit :: editUserModal";
        }
        return "redirect:/users";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute User userDetails,
                             @RequestParam(required = false) String newPassword) {
        userService.updateUser(id, userDetails, newPassword);
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
}
