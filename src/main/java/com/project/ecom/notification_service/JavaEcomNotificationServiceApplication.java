package com.project.ecom.notification_service;

import com.project.ecom.notification_service.configs.SpringMailConfigProperties;
import com.project.ecom.notification_service.kafka.KafkaTopicsConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({SpringMailConfigProperties.class, KafkaTopicsConfigurationProperties.class})
@SpringBootApplication
public class JavaEcomNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaEcomNotificationServiceApplication.class, args);
    }

}
