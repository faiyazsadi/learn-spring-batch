package com.example.learnspringbatch.service.redis;

import com.example.learnspringbatch.entity.redis.CustomerRedis;
import com.example.learnspringbatch.repository.CustomerRepository;
import com.example.learnspringbatch.repository.redis.CustomerRepositoryRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerServiceRedis {
    private final CustomerRepositoryRedis customerRepositoryRedis;
    private final CustomerServiceRedis customerServiceRedis;

    private static Long id = 0L;

    public void save(CustomerRedis customer) {
        customerRepositoryRedis.save(customer);
    }

    public void createAndSave() {
        CustomerRedis customer = new CustomerRedis();
        customer.setId(id++);
        customer.setFirstName("Mark");
        customer.setLastName("Feathers");
        customerServiceRedis.save(customer);
    }
}
