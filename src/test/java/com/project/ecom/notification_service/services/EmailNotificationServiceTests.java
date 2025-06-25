package com.project.ecom.notification_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailNotificationServiceTests {
    @Autowired
    private SimpleMailNotificationService emailService;

    @Test
    void sendNotificationTest() {
        String to = "pumbatimon473@gmail.com";
        String subject = "Spring Boot Java Mail Sender";
        String message = "Testing SimpleMailSender.";
        emailService.sendNotification(to, subject, message);
    }
}
