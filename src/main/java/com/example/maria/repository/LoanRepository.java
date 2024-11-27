package com.example.maria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.maria.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {}
