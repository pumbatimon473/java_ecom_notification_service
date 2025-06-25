package com.project.ecom.notification_service.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.mail")
public class SpringMailConfigProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
}
