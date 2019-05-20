package com.adidas.masterservice.app;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.task.launcher.annotation.EnableTaskLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
@EnableTaskLauncher
@EnableBinding(Source.class)
@EnableScheduling
@EnableConfigurationProperties
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);


    // new metric aviliable here http://localhost:8080/actuator/metrics/CUSTOM-TASK-METRIC
    @Bean
    ApplicationRunner applicationRunner(MeterRegistry meterRegistry) {
        return args -> this.executorService.scheduleAtFixedRate(() ->
                meterRegistry.timer("CUSTOM-TASK-METRIC")
                        .record(Duration.ofMillis((long) (Math.random() * 1000))), 500, 500, TimeUnit.MICROSECONDS);
    }
}
