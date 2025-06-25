package com.project.ecom.notification_service.services;

import com.project.ecom.kafka.events.OrderPlacedEvent;
import jakarta.mail.MessagingException;

public interface IEmailNotificationService {
    void sendNotification(String to, String subject, String message);

    void sendOrderPlacedNotification(OrderPlacedEvent event);
}
