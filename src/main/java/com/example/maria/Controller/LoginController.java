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
    UserRepository userRepository;

    @PostMapping("/login")
    public ModelAndView login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();

        // Fetch user from database
        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            // Store user in session
            session.setAttribute("user", user);
            modelAndView.setViewName("redirect:/dashboard");
        } else {
            modelAndView.setViewName("login");
            modelAndView.addObject("error", "Invalid username or password.");
        }

        return modelAndView;
    }
}
