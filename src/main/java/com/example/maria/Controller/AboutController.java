package com.example.maria.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/html/about")
    public String getAboutPage() {
        return "about";  // Refers to about.html in the resources/templates directory, if using Thymeleaf or similar templating engines
    }
}

