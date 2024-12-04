package com.example.maria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.maria.entity.Loan;
import com.example.maria.entity.Notification;
import com.example.maria.repository.LoanRepository;
import com.example.maria.repository.NotificationRepository;


@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    // Apply for a loan
    public Loan applyLoan(Loan loan) {
        loan.setStatus("PENDING");
        return loanRepository.save(loan);
    }

    // Get all loans
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    // Get loans by user ID (transaction history)
    public List<Loan> getLoanHistory(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    @Transactional
    public Loan approveOrRejectLoan(Long loanId, String status) {
        if (!status.equalsIgnoreCase("APPROVED") && !status.equalsIgnoreCase("REJECTED")) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found with ID: " + loanId));
        
        loan.setStatus(status);
        loanRepository.save(loan);

        // Create and save notification
        Notification notification = new Notification();
        notification.setUserId(loan.getUserId());
        notification.setMessage("Your loan application has been " + status.toLowerCase() + ".");
        notificationRepository.save(notification);

        return loan;
    }
    
}
