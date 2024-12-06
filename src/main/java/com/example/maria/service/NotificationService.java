package com.example.maria.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.maria.entity.Notification;
import com.example.maria.repository.NotificationRepository;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}