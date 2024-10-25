package com.ecommerce.ecommerce;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String index(Model model) {
        return "index";
    }
    @GetMapping("/product")
    public String product(Model model) {
        return "product";
    }   
    @PostMapping("/product")
    @ResponseBody
    public String backTextProduct(@RequestParam("prompt") String hxPrompt) {
        return "<p>Received: " + hxPrompt + "</p>";
    }


}
