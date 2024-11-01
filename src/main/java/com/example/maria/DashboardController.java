package com.example.maria;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class DashboardController {
    
    @GetMapping("/dashboard")
    public String showDashboardPage() {
        return "dashboard";
    }
    
}
