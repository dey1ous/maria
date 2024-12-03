package com.example.maria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maria.entity.Loan;
import com.example.maria.repository.LoanRepository;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

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

    // Approve or reject a loan
    public Loan approveOrRejectLoan(Long loanId, String status) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        loan.setStatus(status);
        return loanRepository.save(loan);
    }
    
}
