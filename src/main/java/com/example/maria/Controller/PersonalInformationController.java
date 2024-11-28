package com.example.maria.Controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.maria.entity.PersonalInformation;
import com.example.maria.service.PersonalInformationService;

@Controller
@RequestMapping("/api/personal-info")
public class PersonalInformationController {

    @Autowired
    private PersonalInformationService service;

    @PostMapping("/submit")
    public String submitPersonalInfo(
        @RequestParam String fullName,
        @RequestParam String dateOfBirth,
        @RequestParam String gender,
        @RequestParam String civilStatus,
        @RequestParam String email,
        @RequestParam String phone,
        @RequestParam String address,
        @RequestParam MultipartFile validId,
        Model model
    ) {
        try {
            // Create a new PersonalInformation object
            PersonalInformation info = new PersonalInformation();
            info.setFullName(fullName);
            info.setDateOfBirth(LocalDate.parse(dateOfBirth));  // Ensure that date format is correct
            info.setGender(gender);
            info.setCivilStatus(civilStatus);
            info.setEmail(email);
            info.setPhone(phone);
            info.setAddress(address);
            info.setValidId(validId.getBytes()); 

            // Save the information to the database
            service.savePersonalInformation(info);

            // Add success message to the model and redirect to a success page
            model.addAttribute("message", "Personal information saved successfully!");
            return "success";  // Redirects to success.html view
        } catch (IOException e) {
            // Add error message to the model and return error page
            model.addAttribute("message", "Failed to upload valid ID: " + e.getMessage());
            return "error";  // Redirects to error.html view
        }
    }
}
