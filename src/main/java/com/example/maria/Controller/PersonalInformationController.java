package com.example.maria.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.maria.entity.PersonalInformation;
import com.example.maria.entity.User;
import com.example.maria.service.PersonalInformationService;
import com.example.maria.service.UserService;

@Controller
@RequestMapping("/api/personal-information")
public class PersonalInformationController {

    @Autowired
    private PersonalInformationService service;
    @Autowired
    private UserService userService;
    private PersonalInformation tempInfo;  // Temporary storage for review

    // Method to review personal information before saving
    @PostMapping("/review")
    public String reviewPersonalInfo(
        @RequestParam("full_name") String fullName,
        @RequestParam("date_of_birth") String dateOfBirth,
        @RequestParam("gender") String gender,
        @RequestParam("civil_status") String civilStatus,
        @RequestParam("email") String email,
        @RequestParam("phone") String phone,
        @RequestParam("address") String address,
        @RequestParam("valid_id") MultipartFile validId,
        Model model
    ) {
        try {
            // Parse date of birth
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(dateOfBirth, formatter);

            // Create and populate the PersonalInformation object
            PersonalInformation info = new PersonalInformation();
            info.setFullName(fullName);
            info.setDateOfBirth(parsedDate);
            info.setGender(gender);
            info.setCivilStatus(civilStatus);
            info.setEmail(email);
            info.setPhone(phone);
            info.setAddress(address);
            info.setValidId(validId.getBytes());

            // Temporarily store the data for review
            tempInfo = info;

            // Add the info object to the model for review
            model.addAttribute("personalInfo", info);

            return "review";  // Directs to review.html

        } catch (IOException e) {
            model.addAttribute("message", "Failed to process valid ID: " + e.getMessage());
            return "error";  // Redirects to error.html
        }
    }

    // Method to confirm and save personal information
    @PostMapping("/confirm")
    public String confirmPersonalInfo(Model model) {
        if (tempInfo != null) {
            try {
                // Save the reviewed personal information to the database
                service.savePersonalInformation(tempInfo);
                model.addAttribute("message", "Personal information saved successfully!");
                tempInfo = null;  // Clear the temporary data after saving
                return "success";  // Redirects to success.html
            } catch (Exception e) {
                model.addAttribute("message", "Error saving personal information: " + e.getMessage());
                return "error";  // Redirects to error.html
            }
        } else {
            model.addAttribute("message", "No data available to confirm!");
            return "error";  // Redirects to error.html
        }
    }

    // Method to allow editing of personal information
    @GetMapping("/edit")
    public String editPersonalInfo(Model model) {
        if (tempInfo != null) {
            // Prefill the registration form with the temporary data
            model.addAttribute("personalInfo", tempInfo);
        }
        return "register";  // Redirects to register.html
    }

    // Method to retrieve and display all personal information for the admin dashboard
    @GetMapping("/all")
    public String getAllPersonalInformation(Model model) {
        List<PersonalInformation> personalInfoList = service.getAllPersonalInformation();
        model.addAttribute("personalInfoList", personalInfoList);
        return "list"; // This will map to list.html to display the data
    }
    @GetMapping("/user/{userId}/about")
    public PersonalInformation getUserPersonalInfo(@PathVariable Long userId) {
        User user = userService.findById(userId);  // Assuming you have a service to find the user
        return service.findByUser(user);  // Get personal info based on user
    }
}
