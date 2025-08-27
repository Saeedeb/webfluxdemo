package com.example.webfluxdemo.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableR2dbcAuditing(auditorAwareRef = "auditorProvider")
//@EntityScan(basePackages = "com.example.project.entities")
@Configuration
public class R2dbcConfig {
}