package com.example.learnspringbatch.controller.redis;

import com.example.learnspringbatch.service.redis.CustomerServiceRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final CustomerServiceRedis customerServiceRedis;

    @PostMapping("/create-customer")
    public void createUser() {
        customerServiceRedis.createAndSave();
    }
}
