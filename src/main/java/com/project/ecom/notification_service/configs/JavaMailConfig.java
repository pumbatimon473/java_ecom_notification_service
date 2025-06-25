package com.project.ecom.notification_service.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class JavaMailConfig {
    private final SpringMailConfigProperties mailConfigProperties;

    @Autowired
    public JavaMailConfig(SpringMailConfigProperties mailConfigProperties) {
        this.mailConfigProperties = mailConfigProperties;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.mailConfigProperties.getHost());
        mailSender.setPort(this.mailConfigProperties.getPort());

        mailSender.setUsername(this.mailConfigProperties.getUsername());
        mailSender.setPassword(this.mailConfigProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
