package com.example.learnspringbatch.service;

import ch.qos.logback.core.util.SystemInfo;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.ThreadFactory;

@AllArgsConstructor
@Service
public class JobSchedulerService {
    @Autowired
    @Qualifier("CustomJobLauncher")
    private JobLauncher jobLauncher;

    private Job job;

    private static final Double CPU_MAX_LOAD_AVERAGE = 4.0;

    public ResponseEntity<Long> importCustomers(String fileName) throws FileNotFoundException {
        // check if the file exists
        Resource resource = new ClassPathResource(fileName);
        if(!resource.exists()) {
            throw new FileNotFoundException("File " + fileName + " Does Not Exist!");
        }

        if(isCpuUsageLow()) {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fileName", fileName)
                    .addLong("startAt", System.currentTimeMillis()).toJobParameters();

            try {
                JobExecution jobExecution = jobLauncher.run(job, jobParameters);
                return ResponseEntity.ok(jobExecution.getId());

            } catch (JobExecutionAlreadyRunningException
                     | JobRestartException
                     | JobInstanceAlreadyCompleteException
                     | JobParametersInvalidException e) {
                e.printStackTrace();
                return ResponseEntity.ok(0L);
            }
        }

        return ResponseEntity.badRequest().build();
    }

    private boolean isCpuUsageLow() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        double cpuLoad = osBean.getSystemLoadAverage();
        return cpuLoad < CPU_MAX_LOAD_AVERAGE;
    }
}
