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
                        // آزاد برای همه
                        .pathMatchers("/users").permitAll()
                        .pathMatchers("/manage/health", "/manage/info").permitAll()
                        .pathMatchers("/manage/prometheus").permitAll()
                        // سایر اکچواتورها نیاز به احراز هویت
                        .pathMatchers("/manage/**").hasRole("ADMIN")
                        // بقیه درخواست‌ها هم نیازمند auth
                        .anyExchange().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }
}
