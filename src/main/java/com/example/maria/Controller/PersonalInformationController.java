package com.example.maria.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.maria.entity.PersonalInformation;
import com.example.maria.entity.User;
import com.example.maria.service.PersonalInformationService;
import com.example.maria.service.UserService;

@RestController
@RequestMapping("/api/personal-information")
public class PersonalInformationController {

    @Autowired
    private PersonalInformationService service;
    @Autowired
    private UserService userService;
    

    // Method to review personal information before saving
    

    @GetMapping("/all")
public List<PersonalInformation> getAllPersonalInformation() {
    List<PersonalInformation> personalInfoList = service.getAllPersonalInformation();
    // Optionally, you can filter out validId and userId here if needed
    return personalInfoList;
}

    @GetMapping("/user/{userId}/about")
    public ResponseEntity<User> getUserDetails(@PathVariable Long userId) {
    User user = userService.getUserDetails(userId);
        return ResponseEntity.ok(user);
    }
    

}
