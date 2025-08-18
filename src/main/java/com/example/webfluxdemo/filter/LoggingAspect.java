package com.example.webfluxdemo.filter;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    //Around is an advice
    //pointcut expression => "execution(* com.example.webfluxdemo.controller..*(..))"
    @Around("execution(* com.example.webfluxdemo.controller..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = pjp.getSignature().toShortString();

        Object[] args = pjp.getArgs();
        log.info("➡️ Entering method: {} with args={}", methodName, args);

        Object result = pjp.proceed();

        if (result instanceof Mono) {
            return ((Mono<?>) result)
                    .doOnSuccess(res -> log.info("⬅️ Completed method: {} with result={} ({} ms)",
                            methodName, res, System.currentTimeMillis() - startTime))
                    .doOnError(err -> log.error("❌ Error in method: {} error={}", methodName, err.getMessage()));

        } else if (result instanceof Flux) {
            return ((Flux<?>) result)
                    .doOnNext(res -> log.info("⬅️ Next item from method: {}", res))
                    .doOnComplete(() -> log.info("✅ Completed Flux method: {} ({} ms)",
                            methodName, System.currentTimeMillis() - startTime))
                    .doOnError(err -> log.error("❌ Error in Flux method: {} error={}", methodName, err.getMessage()));
        }

        return result;
    }
}
