package com.obs.sampleproject.config.aspect;


import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

@Aspect
@RequiredArgsConstructor
@Slf4j
public class LoggingAspect {
    private final Gson gson;

    @Pointcut("within(com.obs.sampleproject.controller.*)")
    public void scopeController() {}

    @Pointcut("within(com.obs.sampleproject.service.*)")
    public void scopeService() {}

    @Before("scopeController()")
    public void handlingNewRequest(JoinPoint joinPoint){
        final var className = joinPoint.getTarget().getClass().getName();
        final var methodName = joinPoint.getSignature().getName();

        log.debug("New request to {}#{}()", className, methodName);
        log.debug("Arguments: ");
        Arrays.stream(joinPoint.getArgs())
                .forEach(arg -> log.debug(gson.toJson(arg)));
    }

    @AfterReturning(
            pointcut = "scopeController()",
            returning = "response"
    )
    public void responseGenerated(JoinPoint joinPoint, ResponseEntity<Object> response) {
        final var className = joinPoint.getTarget().getClass().getName();
        final var methodName = joinPoint.getSignature().getName();

        log.debug("Generated response from {}#{}(): {}", className, methodName, gson.toJson(response));
        MDC.clear();
    }

    @Before("scopeService()")
    public void serviceStart(JoinPoint joinPoint){
        final var className = joinPoint.getTarget().getClass().getName();
        final var methodName = joinPoint.getSignature().getName();
        log.debug("Start service {}#{}()", className, methodName);
    }

    @AfterReturning(
            pointcut = "scopeService()",
            returning = "response"
    )
    public void serviceResponse(JoinPoint joinPoint, Object response){
        final var className = joinPoint.getTarget().getClass().getName();
        final var methodName = joinPoint.getSignature().getName();

        log.debug("End service {}#{}()", className, methodName);
        log.debug("Returned value: {}", gson.toJson(response));
    }
}
