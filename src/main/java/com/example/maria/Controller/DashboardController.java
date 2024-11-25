package com.example.maria.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class DashboardController {
    
    @GetMapping("/dashboard")
    public ModelAndView showDashboardPage() {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        return modelAndView;
    }

}