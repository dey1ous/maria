package com.example.maria.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.maria.entity.PersonalInformation;
import com.example.maria.service.PersonalInformationService;

@Controller
@RequestMapping("/api/personal-information")
public class PersonalInformationController {

    @Autowired
    private PersonalInformationService service;

    private PersonalInformation tempInfo;  // Temporary storage for review
    
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
        // Retrieve the user object from the database
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(dateOfBirth, formatter);
        System.out.println("Parsed Date of Birth: " + parsedDate);

        // Create the PersonalInformation object
        PersonalInformation info = new PersonalInformation();
        info.setFullName(fullName);
        info.setDateOfBirth(parsedDate);
        info.setGender(gender);
        info.setCivilStatus(civilStatus);
        info.setEmail(email);
        info.setPhone(phone);
        info.setAddress(address);
        info.setValidId(validId.getBytes());
       

        // Store temporarily for review
        tempInfo = info;

        // Add the data to the model
        model.addAttribute("personalInfo", info);

        return "review";  // Redirects to review.html
    } catch (IOException e) {
        model.addAttribute("message", "Failed to process valid ID: " + e.getMessage());
        return "error";  // Redirects to error.html
    }
}


    @PostMapping("/confirm")
    public String confirmPersonalInfo(Model model) {
    if (tempInfo != null) {
        try {
            // Save the reviewed data to the database
            service.savePersonalInformation(tempInfo);
            model.addAttribute("message", "Personal information saved successfully!");
            tempInfo = null;  // Clear the temporary data
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


    @GetMapping("/edit")
    public String editPersonalInfo(Model model) {
        if (tempInfo != null) {
            // Add the temporary data to the model to prefill the registration form
            model.addAttribute("personalInfo", tempInfo);
        }
        return "register";  // Redirects to register.html
    }
    @GetMapping("/all")
    public String getAllPersonalInformation(Model model) {
    List<PersonalInformation> personalInfoList = service.getAllPersonalInformation();
    model.addAttribute("personalInfoList", personalInfoList);
    return "list"; // A new Thymeleaf template to display the list
}
}
