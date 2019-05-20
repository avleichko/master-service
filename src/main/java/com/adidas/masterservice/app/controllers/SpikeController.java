package com.adidas.masterservice.app.controllers;

import com.adidas.masterservice.app.services.KafaProducer;
import com.adidas.masterservice.app.services.OperationService;
import com.adidas.masterservice.app.services.OperationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.task.launcher.TaskLaunchRequest;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@EnableBinding(Source.class)
@Slf4j
public class SpikeController {

    @Value("${msg:hello world}")
    private String msg;

    @Autowired
    private Source source;

    @Autowired
    private OperationService operationService;

    @Autowired
    KafaProducer  kafaProducer;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/msg")
    public String getmsg(){
        return msg;
    }

    @GetMapping("/olapic")
    public String olapic (){
        return operationService.launchWorker();
    }

    @GetMapping("/workerStart")
    public String workerStart(){
        log.warn("worker start");
        return "started";
    }
    @GetMapping("/workerEnd")
    public String workerEnd(){
        log.warn("worker end");
        return "end";
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message){
        this.kafaProducer.sendMessage(message);
    }

    @PostMapping(value = "/publishError")
    public void sendMessageErrorToKafkaTopic(@RequestParam("message") String message){
        this.kafaProducer.sendMessageError(message);
    }

}