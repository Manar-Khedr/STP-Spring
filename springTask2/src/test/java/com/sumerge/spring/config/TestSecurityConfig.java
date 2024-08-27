package com.sumerge.spring.config;

import com.sumerge.spring.service.SecurityService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestSecurityConfig {
    @Bean
    public SecurityService securityService() {
        return new SecurityService();
    }
}