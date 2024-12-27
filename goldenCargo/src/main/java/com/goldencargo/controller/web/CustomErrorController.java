package com.goldencargo.controller.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = "Something went wrong. Please try again later.";
        String errorTitle = "Error";

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorMessage = "The page you are looking for does not exist.";
                errorTitle = "Page Not Found";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorMessage = "You do not have permission to access this page.";
                errorTitle = "Access Denied";
            }
        }

        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorTitle", errorTitle);
        return "/helpers/error";
    }


}
