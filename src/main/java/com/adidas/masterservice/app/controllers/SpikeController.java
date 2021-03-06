package com.adidas.masterservice.app.controllers;

import com.adidas.masterservice.app.services.KafkaProducer;
import com.adidas.masterservice.app.services.OperationService;
import com.adidas.product.worker.schema.WorkerFailure;
import com.adidas.product.worker.schema.WorkerLaunch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;
import java.util.Collections;
import java.util.UUID;

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
    private KafkaProducer kafkaProducer;

    @Autowired
    private RestTemplate restTemplate;

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
        WorkerLaunch launch = new WorkerLaunch();
        launch.setId(UUID.randomUUID().toString());
        launch.setBrand("brand");
        launch.setConsumer("consumer");
        launch.setFlow("flow");
        launch.setLocale(Collections.singletonList("en-US"));

        this.kafkaProducer.sendMessage(launch);
    }

    @PostMapping(value = "/publishError")
    public void sendMessageErrorToKafkaTopic(@RequestParam("message") String message){
        WorkerFailure failure = new WorkerFailure();
        failure.setId(UUID.randomUUID().toString());
        failure.setMillis(Clock.systemUTC().millis());
        failure.setConsumer("consumer");
        failure.setException(message);

        this.kafkaProducer.sendMessageError(failure);
    }

}
