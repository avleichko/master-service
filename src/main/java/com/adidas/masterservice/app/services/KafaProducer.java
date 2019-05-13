package com.adidas.masterservice.app.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafaProducer {


    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private static final String TOPIC = "worker-launcher";
    private static final String DEAD_TOPIC = "dead-letter";

    public void sendMessage(String message){
        log.info(String.format("$$ -> Producing message --> %s",message));
        this.kafkaTemplate.send(TOPIC,message);
    }


    public void sendMessageError(String message){
        log.info(String.format("$$ -> Producing message --> %s",message));
        this.kafkaTemplate.send(DEAD_TOPIC, message);
        log.error("dead letter send");
    }

}
