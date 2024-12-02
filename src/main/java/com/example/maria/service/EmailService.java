package com.example.maria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @SuppressWarnings("CallToPrintStackTrace")
    public boolean sendEmail(String name, String email, String subject, String message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo("support@yourdomain.com"); // Replace with your support email
            mailMessage.setSubject(subject);
            mailMessage.setText("From: " + name + " (" + email + ")\n\n" + message);

            mailSender.send(mailMessage);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }
    }
}

