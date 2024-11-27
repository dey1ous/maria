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

    @PostMapping
    public Loan applyForLoan(@RequestBody Loan loanApplication) {
        return loanService.createLoanApplication(loanApplication);
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoanApplications();
    }

    @PutMapping("/{loanId}/approve")
    public Loan approveLoan(@PathVariable Long loanId) {
        return loanService.approveLoan(loanId);
    }

    @PutMapping("/{loanId}/reject")
    public Loan rejectLoan(@PathVariable Long loanId) {
        return loanService.rejectLoan(loanId);
    }
}