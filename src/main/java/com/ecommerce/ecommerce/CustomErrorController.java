//package com.ecommerce.ecommerce;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//
//@Controller
//public class CustomErrorController implements ErrorController {
//
//    @RequestMapping("/custom-error")
//    public String handleCustomError(HttpServletRequest request, Model model) {
//        // Safely retrieve error details
//        Object statusCodeObj = request.getAttribute("jakarta.servlet.error.status_code");
//        Integer statusCode = statusCodeObj != null ? Integer.valueOf(statusCodeObj.toString()) : null;
//        String message = (String) request.getAttribute("jakarta.servlet.error.message");
//
//        // Add attributes for templates
//        model.addAttribute("statusCode", statusCode != null ? statusCode : "Unknown");
//        model.addAttribute("message", message != null ? message : "An unexpected error occurred.");
//
//        // Return specific error pages
//        if (statusCode != null) {
//            if (statusCode == 404) {
//                return "error/404";
//            } else if (statusCode == 500) {
//                return "error/500";
//            }
//        }
//
//        // Fallback for all other cases
//        return "error/default";
//    }
//}
