package com.adidas.masterservice.app.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class KafkaConsumer {

    @Autowired
    OperationService operationService;

    @Autowired
    RestTemplate restTemplate;

    @KafkaListener(topics = "worker-launcher", groupId = "group_id")
    public void consume(String message){
        log.info(String.format("$$ -> Consumed Message -> %s",message));
        log.warn("starting worker");
        final String s = operationService.launchWorker();
        log.warn(s);
    }


    @KafkaListener(topics = "dead-letter", groupId = "group_id")
    public void consumError(String message){
        log.info(String.format("$$ -> Consumed Message -> %s",message));

        final ResponseEntity<ResponseEntity> responseEntityResponseEntity = restTemplate.postForEntity("http://localhost:8086/send", null, ResponseEntity.class);

    }

}
