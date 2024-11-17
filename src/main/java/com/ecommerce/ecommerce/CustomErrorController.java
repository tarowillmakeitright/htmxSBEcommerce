package com.ecommerce.ecommerce;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/custom-error")
    public String handleCustomError(HttpServletRequest request, Model model) {
        // Safely retrieve the status code and message
        Object statusCodeObj = request.getAttribute("jakarta.servlet.error.status_code"); // Note: jakarta.servlet
        Integer statusCode = statusCodeObj != null ? Integer.valueOf(statusCodeObj.toString()) : null;

        String message = (String) request.getAttribute("jakarta.servlet.error.message");

        // Add attributes for use in the error templates
        model.addAttribute("statusCode", statusCode != null ? statusCode : "Unknown");
        model.addAttribute("message", message != null ? message : "An unexpected error occurred.");

        // Handle specific error codes
        if (statusCode != null) {
            switch (statusCode) {
                case 404:
                    return "error/404"; // 404 Not Found
                case 500:
                    return "error/500"; // Internal Server Error
                default:
                    model.addAttribute("errorCode", statusCode); // Include dynamic error codes in default page
                    return "error/default"; // For other status codes
            }
        }

        // Fallback to a generic error page if no status code is present
        return "error/default";
    }
}