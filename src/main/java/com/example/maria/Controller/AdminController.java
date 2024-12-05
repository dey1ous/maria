package com.example.maria.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.maria.entity.Loan;
import com.example.maria.service.LoanService;
import com.example.maria.service.PersonalInformationService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private LoanService loanService;
    @Autowired PersonalInformationService personalInformationService;
    
    @GetMapping("/loans")
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return loans.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(loans);
    }

    @PutMapping("/loans/{loanId}/status")
    public ResponseEntity<Loan> updateLoanStatus(@PathVariable Long loanId, @RequestParam String status) {
        Loan loan = loanService.approveOrRejectLoan(loanId, status);
        return ResponseEntity.ok(loan);
    }
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        // Try to delete the user using the service method
        boolean deleted = personalInformationService.deleteUserById(id);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
}