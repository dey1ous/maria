package com.example.maria.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.maria.service.EmailService;

@RestController
@RequestMapping("/html/contact.html")
public class ContactController {

    @Autowired
    private EmailService emailService;
    public String getContactPage() {
        return "contact"; // This maps to 'contact.html' in the templates directory
    }
    @PostMapping
    public ResponseEntity<String> handleContactForm(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String message) {

        boolean emailSent = emailService.sendEmail(name, email, subject, message);

        if (emailSent) {
            return ResponseEntity.ok("Your message has been sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("There was an error sending your message. Please try again later.");
        }
    }
}
