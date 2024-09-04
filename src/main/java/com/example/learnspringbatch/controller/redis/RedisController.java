package com.example.learnspringbatch.controller.redis;

import com.example.learnspringbatch.entity.redis.CustomerRedis;
import com.example.learnspringbatch.service.redis.CustomerServiceRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/redis/customer")
public class RedisController {

    private final CustomerServiceRedis customerServiceRedis;

    @PostMapping("/create-customer")
    public void createUser() {
        customerServiceRedis.createAndSave();
    }

    @GetMapping("/info/{id}")
    public CustomerRedis customerInfo(@PathVariable("id") Long id) {
        return customerServiceRedis.findById(id);
    }

    @GetMapping("/info/all")
    public List<CustomerRedis> customerInfoAll() {
        return customerServiceRedis.findAll();
    }
}
