package com.example.maria.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // Retrieve session attributes
        String username = (String) session.getAttribute("username");
        String email = (String) session.getAttribute("email");
        String role = (String) session.getAttribute("role");
        String fullName = (String) session.getAttribute("fullName");

        if (username == null) {
            // User is not logged in; redirect to login
            return "redirect:/login";
        }

        // Populate model for the dashboard view
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("role", role);
        model.addAttribute("fullName", fullName);

        // Direct to different dashboards based on the role
        if ("admin".equalsIgnoreCase(role)) {
            return "admindashboard"; // Admin-specific dashboard
        } else {
            return "dashboard"; // User-specific dashboard
        }
    }
    
}
