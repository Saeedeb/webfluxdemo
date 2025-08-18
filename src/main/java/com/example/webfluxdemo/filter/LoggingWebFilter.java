package com.example.webfluxdemo.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long startTime = System.currentTimeMillis();

        log.info("➡️ Incoming request: [{}] {}",
                exchange.getRequest().getMethod(),
                exchange.getRequest().getURI());
        return chain.filter(exchange)
                .doOnSuccess(done -> {
                    long duration = System.currentTimeMillis() - startTime;
                    log.info("⬅️ Outgoing response: status={} duration={}ms",
                            exchange.getResponse().getStatusCode(),
                            duration);
                });
    }
}
