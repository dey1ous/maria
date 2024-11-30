package com.example.maria.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.maria.entity.Loan;
import com.example.maria.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // Apply for a new loan
    @PostMapping
    public Loan applyForLoan(@RequestBody Loan loanApplication) {
        return loanService.applyLoan(loanApplication);
    }

    // Get all loans (for admin dashboard)
    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    // Approve a loan application
    @PutMapping("/{loanId}/approve")
    public Loan approveLoan(@PathVariable Long loanId) {
        return loanService.approveOrRejectLoan(loanId, "APPROVED");
    }

    // Reject a loan application
    @PutMapping("/{loanId}/reject")
    public Loan rejectLoan(@PathVariable Long loanId) {
        return loanService.approveOrRejectLoan(loanId, "REJECTED");
    }

    // Get transaction history for a specific user
    @GetMapping("/user/{userId}")
    public List<Loan> getTransactionHistoryByUserId(@PathVariable Long userId) {
        return loanService.getLoanHistory(userId);
    }
}
