package com.typeface.assignment.filestorageservice.infrastructure.external.mysql.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceConfigurationProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private long connectionTimeout;
    private String maxLifetime;

}
