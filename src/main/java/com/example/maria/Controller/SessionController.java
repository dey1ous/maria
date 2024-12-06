package com.example.maria.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class SessionController {

    @Autowired
    private HttpSession session;

    // This endpoint will return the userId stored in the session
    @GetMapping("/api/session/userId")
    public Long getUserId() {
        // Retrieve the userId from the session
        Long userId = (Long) session.getAttribute("userId");

        if (userId != null) {
            return userId;
        } else {
            // Return null or handle the case when userId is not found
            return null;
        }
    }
}
