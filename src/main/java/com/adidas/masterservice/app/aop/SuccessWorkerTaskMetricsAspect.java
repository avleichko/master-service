package com.adidas.masterservice.app.aop;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Aspect
@Configuration
@Slf4j
public class SuccessWorkerTaskMetricsAspect {


    @Autowired
    MeterRegistry meterRegistry;

    @After("execution(* com.adidas.masterservice.app.services.KafkaProducer.sendMessage(..))")
    public void afterMethod(JoinPoint joinPoint){
        final String arg = joinPoint.getArgs()[0].toString();
        meterRegistry.counter("SuccessWorkerStartTask",  Tags.of("workerTaskId", arg, "Time", new Date().toString()) ).increment(1);
    }
}
