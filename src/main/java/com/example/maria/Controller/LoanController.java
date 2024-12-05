package com.example.maria.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final LoanService loanService;
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }


    // Apply for a loan
    @PostMapping
    @SuppressWarnings("UseSpecificCatch")
public ResponseEntity<?> applyLoan(@RequestBody Map<String, Object> request) {
    try {
        double amount = Double.parseDouble(request.get("amount").toString());
        String name = (String) request.get("name");
        // Create and save a new loan
        Loan loan = new Loan();
        loan.setName(name);
        loan.setAmount(amount);
        loan.setApplicationDate(LocalDateTime.now());
        loan.setStatus("PENDING");

        loanService.applyLoan(loan);
        return ResponseEntity.ok(Map.of("message", "Loan application submitted successfully!"));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Failed to apply for loan: " + e.getMessage()));
    }
}


    // Get all loans
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


    @GetMapping("/by-name/{name}")
    public ResponseEntity<List<Loan>> getLoanHistory(@PathVariable String name) {
        List<Loan> loans = loanService.findLoansByName(name);
        return loans.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(loans);
    }

}
