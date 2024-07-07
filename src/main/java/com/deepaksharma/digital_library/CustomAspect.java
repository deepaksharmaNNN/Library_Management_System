package com.deepaksharma.digital_library;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CustomAspect {

    //Aspects for controller
    @Before("execution(* com.deepaksharma.digital_library.controller.BookController.getAllBooks(..))")
    public void emitBeforeLogs(JoinPoint joinPoint){
        log.info("Emit logs Before Invoked: {}", joinPoint.getSignature());
    }
    @After("execution(* com.deepaksharma.digital_library.controller.BookController.getAllBooks(..))")
    public void emitAfterLogs(JoinPoint joinPoint){
        log.info("Emit logs After Invoked: {}", joinPoint.getSignature());
    }

    @Around("execution(* com.deepaksharma.digital_library.service.BookService.getAllBooks(..))")
    public Object emitAroundLogs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        log.info("Emit logs Around Invoked: {}", proceedingJoinPoint.getSignature());
        Object result = proceedingJoinPoint.proceed();
        log.info("Emit logs Around Invoked: {}", proceedingJoinPoint.getSignature());
        return result;
    }
}
