package com.example.learnspringbatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

@Configuration
public class AppConfig {
    @Async("taskExecutor")
    public void task() {
        // heavy task
        System.out.println(Thread.currentThread().getName());
        sleep(5000);
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Bean
    public String simpleName() {
        return "name";
    }


    @Bean
    public String simpleName2() {
        return "name2";
    }
}
