package com.adidas.masterservice.app.aop;

import com.adidas.masterservice.app.dto.MigrationFlow;
import com.adidas.masterservice.app.dto.WorkerStarterDto;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Aspect
@Configuration
@Slf4j
public class MetricsAspect {


    @Autowired
    MeterRegistry meterRegistry;

    @After("execution(* com.adidas.masterservice.app.controllers.*.*(..))")
    public void before(JoinPoint joinPoint){
        final WorkerStarterDto arg = (WorkerStarterDto)joinPoint.getArgs()[0];
        meterRegistry.counter("workerLauncher",  Tags.of("workerTaskId", arg.getUuid().toString(), "Time", new Date().toString()) ).increment(1);
    }
}
