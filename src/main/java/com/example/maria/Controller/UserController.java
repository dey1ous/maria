package com.example.maria.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.maria.entity.Loan;
import com.example.maria.service.LoanService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private LoanService loanService;

    // Endpoint to submit a loan application
    @PostMapping("/loan")
    public ResponseEntity<String> applyForLoan(@RequestBody Loan loan) {
        try {
            loanService.applyLoan(loan); // Call service to process loan
            return ResponseEntity.ok("Loan submitted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error submitting loan.");
        }
    }

    // Endpoint to get a user's transaction history
    @GetMapping("/loans/{userId}")
    public ResponseEntity<List<Loan>> getLoanHistory(@PathVariable Long userId) {
        List<Loan> loans = loanService.getLoanHistory(userId);
        return loans.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(loans);
    }
}
