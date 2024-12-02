package com.example.maria.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.maria.repository.UserRepository;

@RestController
public class UsernameCheckController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/check-username")
    public Map<String, Boolean> checkUsername(@RequestParam("username") String username) {
        boolean exists = userRepository.findByUsername(username) != null;
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return response;
    }
    
}
