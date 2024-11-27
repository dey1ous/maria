package com.example.maria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.maria.entity.Transaction;



@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {}