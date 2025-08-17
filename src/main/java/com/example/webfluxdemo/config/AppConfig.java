package com.example.webfluxdemo.config;


import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public static ConnectionFactory connectionFactory() {
        return ConnectionFactories.get("r2dbc:postgresql://localhost:5432/postgres?user=postgres&password=S@B205080");
    }
}