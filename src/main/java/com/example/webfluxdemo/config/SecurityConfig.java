package com.example.webfluxdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain; // ✅ درست برای WebFlux

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchanges -> exchanges
                        // مسیرهای آزاد
                        .pathMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/webjars/**").permitAll()
                        .pathMatchers("/users/**").permitAll()
                        .pathMatchers("/manage/health", "/manage/info").permitAll()
                        .pathMatchers("/manage/prometheus").permitAll()
                        // مسیرهای مدیریت
                        .pathMatchers("/manage/**").hasRole("ADMIN")
                        // بقیه مسیرها auth لازم دارند
                        .anyExchange().authenticated()
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .anonymous(Customizer.withDefaults())
                .build();
    }
}
