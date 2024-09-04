package com.example.learnspringbatch.entity.redis;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("customer")
public class CustomerRedis implements Serializable {
    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String gender;

    private String contactNo;

    private String country;

    private String dob;
}
