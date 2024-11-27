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
        // Retrieve the user from the session
        User user = (User) session.getAttribute("user");

        if (user != null) {
            // Add user details to the model so it can be accessed in the view
            model.addAttribute("user", user);
            return "dashboard";  // Return the dashboard view
        } else {
            // If no user is found in the session, redirect to login page
            return "redirect:/login";
        }
    }
}
