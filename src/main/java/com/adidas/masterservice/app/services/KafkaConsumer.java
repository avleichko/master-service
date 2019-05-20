package com.adidas.masterservice.app.services;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumer {

    @Autowired
    OperationService operationService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MeterRegistry meterRegistry;

    Map<String, Integer> errorKafka = new HashMap<>();

    @KafkaListener(topics = "worker-launcher", groupId = "group_id")
    public void consume(String message){
        log.info(String.format("$$ -> Consumed Message -> %s",message));
        log.warn("starting worker");
        final String s = operationService.launchWorker();
        log.warn(s);
    }

    @KafkaListener(topics = "worker-result", groupId = "group_id")
    public void consumeResult(String message){
        log.info(String.format("$$ -> Consumed Message -> %s", message));
        log.warn("result worker");
    }


    @KafkaListener(topics = "dead-letter", groupId = "group_id")
    public void consumError(String message){
        log.info(String.format("$$ -> Consumed Message -> %s",message));

        final Integer integer = errorKafka.get(message);
        if (integer == null){
            errorKafka.put(message,0);
        }else{
            errorKafka.put(message, integer+1);
        }

        final ResponseEntity<ResponseEntity> responseEntityResponseEntity = restTemplate.postForEntity("http://localhost:8086/send", null, ResponseEntity.class);

        meterRegistry.gaugeMapSize("kafka-error-adidas", Tags.of("message", message), errorKafka);

        meterRegistry.gauge("kafka-error-adidas-summary-"+message,Tags.of("asd",message),errorKafka.get(message));
    }

}
