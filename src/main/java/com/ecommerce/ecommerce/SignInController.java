package com.ecommerce.ecommerce;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {

    @GetMapping("login")
    public String index() {
        return "login"; // Render the login page
    }

    @GetMapping("/home/user")
    public String userProfile(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        if (oAuth2User == null) {
            // Redirect to login page if the user is not authenticated
            return "redirect:/login";
        }

        // Debug: Log all attributes to ensure data is available
        oAuth2User.getAttributes().forEach((key, value) -> System.out.println(key + ": " + value));

        // Extract user information
        String pictureUrl = oAuth2User.getAttribute("picture");
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");

        // Add attributes to the model
        model.addAttribute("pictureUrl", pictureUrl != null ? pictureUrl : "#");
        model.addAttribute("name", name != null ? name : "Anonymous User");
        model.addAttribute("email", email != null ? email : "No Email Available");

        return "user"; // Render user.html template
    }
    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout-success"; // Render logout.html
    }
}