package com.example.maria;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @PostMapping("/login")
    public ModelAndView login(
            @RequestParam("id") String email,
            @RequestParam("password") String password) {
        
        ModelAndView modelAndView = new ModelAndView();

      
        if ("test@example.com".equals(email) && "password123".equals(password)) {
            modelAndView.setViewName("home"); // Redirect to home page on successful login
        } else {
            modelAndView.setViewName("login"); // Redirect back to login page on failure
            modelAndView.addObject("error", "Invalid email or password.");
        }

        return modelAndView;
    }
}