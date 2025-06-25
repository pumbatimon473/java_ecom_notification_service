package com.project.ecom.notification_service.kafka.consumers;

import com.project.ecom.kafka.events.OrderPlacedEvent;
import com.project.ecom.notification_service.services.IEmailNotificationService;
import com.project.ecom.notification_service.services.SimpleMailNotificationService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedEventListener {
    private final IEmailNotificationService emailService;
    private static final Logger logger = LoggerFactory.getLogger(OrderPlacedEventListener.class);

    @Autowired
    public OrderPlacedEventListener(@Qualifier(value = "mimeMailNotificationService") IEmailNotificationService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${kafka.topic.order-placed}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleOrderPlacedEvent(OrderPlacedEvent event) throws MessagingException {
        try {
            this.emailService.sendOrderPlacedNotification(event);
            OrderPlacedEventListener.logger.info("NOTIFICATION SENT FOR CONFIRMED ORDER #{}", event.getOrderId());
        } catch (Exception e) {
            OrderPlacedEventListener.logger.error("FAILED TO SEND NOTIFICATION FOR ORDER #{}", event.getOrderId(), e);
        }
    }
}
