package com.example.stock.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class LettuceLockAspect {
    private final LettuceLockService lockService;

    public LettuceLockAspect(LettuceLockService lockService) {

        this.lockService = lockService;
    }

    @Around("@annotation(com.example.stock.aop.LettuceLock)&& args(id,..)")
    public Object lettuceLock(ProceedingJoinPoint pjp, Long id) throws Throwable {
        while (!lockService.lock(id)) {
            sleep(100);
        }

        try {
            return pjp.proceed();
        } finally {
            lockService.unlock(id);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
