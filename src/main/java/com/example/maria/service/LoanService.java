package com.example.maria.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.maria.entity.Loan;
import com.example.maria.repository.LoanRepository;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    public Loan createLoanApplication(Loan loanApplication) {
        loanApplication.setStatus("PENDING");
        loanApplication.setApplicationDate(new Date());
        return loanRepository.save(loanApplication);
    }

    public List<Loan> getAllLoanApplications() {
        return loanRepository.findAll();
    }

    public Loan approveLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        loan.setStatus("APPROVED");
        return loanRepository.save(loan);
    }

    public Loan rejectLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        loan.setStatus("REJECTED");
        return loanRepository.save(loan);
    }
}
