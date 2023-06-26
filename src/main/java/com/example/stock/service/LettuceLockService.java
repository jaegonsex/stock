package com.example.stock.service;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class LettuceLockService {

    private final RedisTemplate<String, String> redisTemplate;

    public LettuceLockService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Boolean lock(Long key) {
        return redisTemplate
                .opsForValue()
                .setIfAbsent(getLockKey(key), "lock", Duration.ofMillis(5000));
    }

    public void unlock(Long key) {
        redisTemplate.delete(getLockKey(key));
    }

    private static String getLockKey(Long key) {
        return key.toString();
    }

}
