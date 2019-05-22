package com.adidas.masterservice.app.services;

import com.adidas.masterservice.app.exceptions.CommonMasterServiceException;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
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

    private final Map<String, Integer> errorKafka = new HashMap<>();

    @KafkaListener(topics = "worker-launcher", groupId = "group_id")
    public void consume(String message) {
        log.info(String.format("$$ -> Consumed Message -> %s", message));
        log.warn("starting worker");
        final String s = operationService.launchWorker();
        log.warn(s);
    }

    @KafkaListener(topics = "worker-result", groupId = "group_id")
    public void consumeResult(String message) {
        log.info(String.format("$$ -> Consumed Message -> %s", message));
        log.warn("result worker");
    }


    @KafkaListener(topics = "dead-letter", groupId = "group_id")
    public void consumeError(String message) {
        log.info(String.format("$$ -> Consumed Message -> %s", message));

        try {
            final ResponseEntity<ResponseEntity> responseEntityResponseEntity = restTemplate.postForEntity("http://localhost:8086/send", null, ResponseEntity.class);
            log.info(responseEntityResponseEntity.toString());
        } catch (ResourceAccessException e) {
            log.error("unable to send notification to notification service "+ e.getMessage());
            log.warn("following msg was not sent to notification service : "+ message);
           // throw new CommonMasterServiceException("Notification service is unaviliable", e);
        }
    }

}
