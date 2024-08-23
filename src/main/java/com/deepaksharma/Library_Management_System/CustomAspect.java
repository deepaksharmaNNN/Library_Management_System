package com.deepaksharma.Library_Management_System;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CustomAspect {

    @Before("execution(* com.deepaksharma.Library_Management_System.controller.*.*(..))")
    public void beforeController(JoinPoint joinPoint) {
        log.info("Before controller method : {}", joinPoint.getSignature());
    }


    @After("execution(* com.deepaksharma.Library_Management_System.controller.*.*(..))")
    public void afterController(JoinPoint joinPoint) {
        log.info("After controller method : {}", joinPoint.getSignature());
    }

    @Around("execution(* com.deepaksharma.Library_Management_System.service.*.*(..))")
    public Object aroundService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Around service method before execution : {}", proceedingJoinPoint.getSignature());
        Object response = proceedingJoinPoint.proceed();
        log.info("Around service method response : {}", response);
        log.info("Around service method after execution : {}", proceedingJoinPoint.getSignature());
        return response;
    }
}
