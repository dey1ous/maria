package com.example.maria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.maria.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userId); // Retrieve loans by User ID
    List<Loan> findByStatus(String status); // Retrieve loans by status
}
