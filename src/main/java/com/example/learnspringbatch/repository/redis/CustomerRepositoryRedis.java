package com.example.learnspringbatch.repository.redis;

import com.example.learnspringbatch.entity.redis.CustomerRedis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepositoryRedis extends CrudRepository<CustomerRedis, Long> {
}
