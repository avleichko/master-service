package com.adidas.masterservice.app.aop;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 *  Metric Describes amount of objects which were sent to dead letter topic
 * @author Aleksandr Velichko
 *
 * */
@Aspect
@Configuration
@Slf4j
public class NotificationServiceUnAvaliableMetricsAspect {


    private static final String ERROR_DESCRIPTION = "ERROR_DESCRIPTION";
    private static final String TIME = "Time";
    private static final String ERRORS_ON_WORKERS_SIDE = "FailedSendedErrorMsg";

    @Autowired
    MeterRegistry meterRegistry;

    @AfterThrowing("execution(* com.adidas.masterservice.app.services.KafkaConsumer.consumeError(..))")
    public void afterMethod(JoinPoint joinPoint){
        final String arg = joinPoint.getArgs()[0].toString();
        meterRegistry.counter(ERRORS_ON_WORKERS_SIDE,  Tags.of(ERROR_DESCRIPTION, arg, TIME, new Date().toString()) ).increment(1);
    }
}
