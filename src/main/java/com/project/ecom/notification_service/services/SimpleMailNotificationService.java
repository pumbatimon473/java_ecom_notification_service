package com.project.ecom.notification_service.services;

import com.project.ecom.dtos.OrderItemDto;
import com.project.ecom.kafka.events.OrderPlacedEvent;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleMailNotificationService implements IEmailNotificationService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String MAIL_USERNAME;

    @Autowired
    public SimpleMailNotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendNotification(String to, String subject, String message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(MAIL_USERNAME);
            mailMessage.setTo(to);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            this.javaMailSender.send(mailMessage);
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendOrderPlacedNotification(OrderPlacedEvent event) {
        this.sendNotification(
                event.getCustomerInfo().getEmail(),
                String.format("Your Order #%d is Confirmed", event.getOrderId()),
                this.generateOrderMessage(event)
        );
    }

    private String generateOrderMessage(OrderPlacedEvent event) {
        StringBuilder builder = new StringBuilder();
        builder.append("Thank you ").append(event.getCustomerInfo().getFirstName()).append(" for shopping with us.\n")
                .append("\nTotal Amount: Rs ").append(event.getTotal()).append("/-\n")
                .append("Transaction ID: ").append(event.getTransactionId()).append("\n");

        builder.append("\nOrder Summary: Order #").append(event.getOrderId()).append("\n");
        for (OrderItemDto orderItem : event.getOrderItems()) {
            builder.append(orderItem.getProduct().getName()).append(" x ").append(orderItem.getQuantity()).append("\n");
        }
        builder.append("\nYour order will be shipped within the next 24 hours.");
        return builder.toString();
    }
}
