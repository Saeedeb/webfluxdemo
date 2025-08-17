package com.example.webfluxdemo.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomHealthConfig {
    @Bean
    public HealthIndicator ExternalServiceHealth() {
        return () -> {
            boolean serviceUp = checkService();
            if (serviceUp) {
                return Health.up().withDetail("service", "Running").build();
            } else {
                return Health.down().withDetail("service", "Not Responding").build();
            }
        };
    }

    @Bean
    public HealthIndicator ExternalServiceHealth2() {
        return () -> {
            boolean serviceUp = checkService();
            if (serviceUp) {
                return Health.up().withDetail("service", "Running").build();
            } else {
                return Health.down().withDetail("service", "Not Responding").build();
            }
        };
    }


    private boolean checkService() {

        return true;
    }
}

