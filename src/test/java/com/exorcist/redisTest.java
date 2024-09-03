package com.exorcist;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class redisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testSet(){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("50","6666",15, TimeUnit.SECONDS);
    }
    @Test
    public void testGet(){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        System.out.println(ops.get("50"));
    }
}
