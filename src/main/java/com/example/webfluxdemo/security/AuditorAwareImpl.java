package com.example.webfluxdemo.security;

import lombok.NonNull;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("auditorProvider")
public class AuditorAwareImpl implements ReactiveAuditorAware<Long> {


    @Override
    @NonNull
    public Mono<Long> getCurrentAuditor() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getPrincipal())
                //.cast(CustomUserDetails.class)   // حالا cast روی principal
                //.map(CustomUserDetails::getId);
                .flatMap(principal -> {
                    if (principal instanceof CustomUserDetails cud) {
                        return Mono.just(cud.getId());
                    } else {
                        return Mono.empty();
                    }
                });
    }

}