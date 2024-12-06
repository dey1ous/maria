package com.example.maria.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
@RequestMapping("/personal-information")
public class PersonalInformationPageController {

    @Autowired
    private PersonalInformationService service;
    private PersonalInformation tempInfo;
    private static final String VALID_ID_STORAGE_DIR = "src/main/resources/static/images";
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
            String validIdFilename = saveValidIdToFile(validId);
            info.setValidIdFilename(validIdFilename);

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
     private String saveValidIdToFile(MultipartFile validId) throws IOException {
        // Generate a unique filename
        String filename = "valid_id_" + System.currentTimeMillis() + ".jpg"; // Change extension as needed
        Path path = Paths.get(VALID_ID_STORAGE_DIR, filename);

        // Save the file
        Files.write(path, validId.getBytes());
        
        return filename;  // Return the filename to store in the database
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

}