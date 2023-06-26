package com.example.stock.service;

import com.example.stock.aop.Retry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object retry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        int limitCount = retry.limitCount();

        Exception exceptionHolder = null;

        for (int retryCount = 1; retryCount <= limitCount; retryCount++) {

            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                exceptionHolder = (Exception) e;
            }
        }
        throw exceptionHolder;
    }
}
