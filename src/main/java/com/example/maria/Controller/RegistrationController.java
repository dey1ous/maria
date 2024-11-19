package com.example.maria.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RegistrationController {

   
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "Registration"; 
    }

    @PostMapping("/register")
    public RedirectView registerUser(
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
      
        System.out.println("User registered with Email: " + email + ", Name: " + name + ", Username: " + username);

        return new RedirectView("/login");
    }

    
    @GetMapping("/login")
    public String showLoginForm() {
        return "Login"; 
    }
}
