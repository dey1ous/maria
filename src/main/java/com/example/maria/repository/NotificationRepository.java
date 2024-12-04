package com.example.maria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.maria.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Additional query methods can be defined here if needed
}