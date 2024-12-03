package com.example.maria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maria.entity.User;
import com.example.maria.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public boolean registerUser(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);  // Return null if user not found
    }
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
               
    }
}