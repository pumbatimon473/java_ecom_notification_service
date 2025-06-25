package com.project.ecom.notification_service.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "kafka.topic")
public class KafkaTopicsConfigurationProperties {
    private String orderCreated;
    private String orderPlaced;
}

