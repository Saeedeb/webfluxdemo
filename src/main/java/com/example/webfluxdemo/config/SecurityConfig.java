package com.example.webfluxdemo.config;

import com.example.webfluxdemo.service.CustomReactiveUserDetailsService;
import com.example.webfluxdemo.service.UserRoleService;
import com.example.webfluxdemo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                        .pathMatchers("/users/**").hasRole("admin")
                        .pathMatchers("/manage/health", "/manage/info").permitAll()
                        .pathMatchers("/manage/prometheus").permitAll()
                        // مسیرهای مدیریت
                        .pathMatchers("/manage/**").hasRole("ADMIN")
                        // بقیه مسیرها auth لازم دارند
                        .anyExchange().authenticated()
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(Customizer.withDefaults())
                .anonymous(Customizer.withDefaults())
                .build();
    }

//    @Bean
//    public MapReactiveUserDetailsService mapReactiveUserDetailsService() {
//        return null;
//    }

  //  @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchanges -> exchanges.anyExchange().permitAll())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .build();
    }
    @Bean
    public ReactiveUserDetailsService userDetailsService(UserService userService, UserRoleService userRoleService){
        return new CustomReactiveUserDetailsService(userService,userRoleService);
    }

    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        // bcrypt به صورت پیشفرض
//    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
