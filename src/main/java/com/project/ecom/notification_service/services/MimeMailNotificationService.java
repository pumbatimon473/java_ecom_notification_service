package com.project.ecom.notification_service.services;

import com.project.ecom.dtos.OrderItemDto;
import com.project.ecom.kafka.events.OrderPlacedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MimeMailNotificationService implements IEmailNotificationService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String MAIL_USERNAME;

    @Autowired
    public MimeMailNotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendNotification(String to, String subject, String message) {
        try {
            MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);  // multipart = true
            helper.setFrom(MAIL_USERNAME);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);  // html = true
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendOrderPlacedNotification(OrderPlacedEvent event) {
        this.sendNotification(
                event.getCustomerInfo().getEmail(),
                String.format("Your Order #%d is Confirmed", event.getOrderId()),
                this.generateHtmlMessage(event)
        );
    }

    private String generateHtmlMessage(OrderPlacedEvent event) {
        StringBuilder builder = new StringBuilder();
        builder.append("<h2>Order Confirmation - Order #").append(event.getOrderId()).append("</h2>");
        builder.append("<p>Thank you ").append(event.getCustomerInfo().getFirstName()).append(" for shopping with us! Your order has been placed successfully.</p>");
        builder.append("<p><strong>Transaction ID:</strong> ").append(event.getTransactionId()).append("</p>");
        builder.append("<p><strong>Total Amount:</strong> Rs ").append(event.getTotal()).append("/-</p>");
        builder.append("<br><h4>Order Summary:</h4>");
        builder.append("<table border='1' cellspacing='0' cellpadding='5'>")
                .append("<tr><th>Product</th><th>Quantity</th></tr>");
        for (OrderItemDto item : event.getOrderItems()) {
            builder.append("<tr>")
                    .append("<td>").append(item.getProduct().getName()).append("</td>")
                    .append("<td>").append(item.getQuantity()).append("</td>")
                    .append("</tr>");
        }
        builder.append("</table>");
        builder.append("<br><p>We will notify you once your order is shipped.</p>");
        return builder.toString();
    }
}
