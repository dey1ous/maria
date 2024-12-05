package com.example.maria.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.maria.entity.User;
import com.example.maria.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ModelAndView login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();

        // Fetch user from database
        User user = userRepository.findByUsername(username);

        if (user == null) {
            // Username does not exist
            modelAndView.setViewName("login");
            modelAndView.addObject("error", "Username does not exist.");
        } else if (!user.getPassword().equals(password)) {
            // Password does not match
            modelAndView.setViewName("login");
            modelAndView.addObject("error", "Incorrect password.");
        } else {
            // Store essential user information in the session (avoid sensitive data)
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("role", user.getRole());
            session.setAttribute("fullName", user.getFullName());

            modelAndView.setViewName("redirect:/dashboard");
        }

        return modelAndView;
    }
}
