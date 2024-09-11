package com.example.learnspringbatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RedisMessageListener implements MessageListener {
    @Qualifier("CustomJobLauncher")
    private final JobLauncher jobLauncher;
    private final Job job;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String receivedMessage = new String(message.getBody());

        String command = receivedMessage.split(":")[0].trim();
        String jobId = receivedMessage.split(":")[1].trim();


        if ("START_JOB".equals(command)) {
            try {
                String jobData = redisTemplate.opsForHash().multiGet(jobId, List.of("name", "fileName", "status")).toString();
                String fileName = Objects.requireNonNull(redisTemplate.opsForHash().get(jobId, "fileName")).toString();

                System.out.println("Information: " + jobData);

                JobParameters jobParameters = new JobParametersBuilder()
                        .addString("fileName", fileName)
                        .addLong("startAt", System.currentTimeMillis()).toJobParameters();

                jobLauncher.run(job, jobParameters);

                System.out.println("Batch job started successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(this, new ChannelTopic("batch-job-channel"));
        return container;
    }
}
