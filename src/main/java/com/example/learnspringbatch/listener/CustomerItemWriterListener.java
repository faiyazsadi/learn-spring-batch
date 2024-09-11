package com.example.learnspringbatch.listener;

import com.example.learnspringbatch.entity.Customer;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;


public class CustomerItemWriterListener implements ItemWriteListener<Customer> {
    @Override
    public void onWriteError(Exception exception, Chunk<? extends Customer> items) {
        System.err.println("Error: " + exception.getMessage());
    }
}
