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

/**
 * Metric Describes amount of all exceptions which were thrown by Operation Controller endpoint
 * @author Aleksandr Velichko
 *
 * */
@Aspect
@Configuration
@Slf4j
public class OperationEndPointMetricsAspect {


    private static final String ERROR_DESCRIPTION = "ERROR_DESCRIPTION";
    private static final String TIME = "Time";
    private static final String OPERATION_END_POINT_ERROR_METRICS = "OperationEndPointErrorMetrics";

    @Autowired
    MeterRegistry meterRegistry;

    @After("execution(* com.adidas.masterservice.app.exceptions.ErrorControllerAdvice.*(..)))")
    public void afterMethod(JoinPoint joinPoint){
        final String arg = joinPoint.getArgs()[0].toString();
        meterRegistry.counter(OPERATION_END_POINT_ERROR_METRICS,  Tags.of(ERROR_DESCRIPTION, arg, TIME, new Date().toString()) ).increment(1);
    }
}
