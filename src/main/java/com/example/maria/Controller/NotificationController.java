package com.example.maria.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.maria.entity.Loan;
import com.example.maria.entity.Notification;
import com.example.maria.repository.LoanRepository;
import com.example.maria.repository.NotificationRepository;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    // Get notifications by loanId
    @GetMapping("/by-loan/{loanId}")
    public List<Notification> getNotificationsByLoanId(@PathVariable Long loanId) {
        // Fetch the loan using loanId
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        
        if (loanOptional.isPresent()) {
            // Retrieve the userId from the loan and fetch notifications
            Long userId = loanOptional.get().getUserId();
            return notificationRepository.findByUserId(userId); // Fetch notifications for that userId
        } else {
            throw new IllegalArgumentException("Loan not found with ID: " + loanId);
        }
    }
}
