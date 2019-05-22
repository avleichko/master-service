package com.adidas.masterservice.app.services;

import com.adidas.masterservice.app.exceptions.CommonMasterServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class KafkaConsumer {

    private final OperationService operationService;

    private final RestTemplate restTemplate;

    @Autowired
    public KafkaConsumer(OperationService operationService, RestTemplate restTemplate) {
        this.operationService = operationService;
        this.restTemplate = restTemplate;
    }

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
            throw new CommonMasterServiceException("unable to send notification to notification service ", e);
        }
    }

}
