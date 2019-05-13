package com.adidas.masterservice.app.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @Autowired
    OperationService operationService;

    @KafkaListener(topics = "worker-launcher", groupId = "group_id")
    public void consume(String message){
        log.info(String.format("$$ -> Consumed Message -> %s",message));
        log.warn("starting worker");
        final String s = operationService.launchWorker();
        log.warn(s);
    }

}
