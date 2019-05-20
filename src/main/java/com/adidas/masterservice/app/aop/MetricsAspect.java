package com.adidas.masterservice.app.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class MetricsAspect {

    @After("execution(* com.adidas.masterservice.app.controllers.*.*(..))")
    public void before(JoinPoint joinPoint){
        //Advice
        log.error(" Check for user access ");
        log.error(" Allowed execution for {}", joinPoint);
    }
}
