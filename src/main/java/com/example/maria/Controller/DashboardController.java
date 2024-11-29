package com.example.maria.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.maria.entity.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {
    

    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

         if (user != null) {
            if ("admin".equalsIgnoreCase(user.getRole())) {
                return "admindashboard";  
            } else {
                model.addAttribute("user", user);
                return "dashboard";  
            }
        } else {
            return "redirect:/login";
        }
    }
}
