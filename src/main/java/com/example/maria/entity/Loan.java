package com.example.maria.entity;



import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Double amount;
    private String status; // "PENDING", "APPROVED", "REJECTED"

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime applicationDate;

    public void setStatus(String string) {

        throw new UnsupportedOperationException("Unimplemented method 'setStatus'");
    }

    public void setApplicationDate(java.util.Date date) {
    
        throw new UnsupportedOperationException("Unimplemented method 'setApplicationDate'");
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }
}