package com.adidas.masterservice.app.services;

import com.adidas.masterservice.app.exceptions.CommonMasterServiceException;
import com.adidas.product.worker.schema.ResultSchema;
import com.adidas.product.worker.schema.WorkerFailure;
import com.adidas.product.worker.schema.WorkerLaunch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@KafkaListener(topics = {"worker-launcher", "worker-result", "dead-letter"},
        containerFactory = "concurrentListener")
public class KafkaConsumer {
    private final OperationService operationService;

    private final RestTemplate restTemplate;

    @Autowired
    public KafkaConsumer(OperationService operationService, RestTemplate restTemplate) {
        this.operationService = operationService;
        this.restTemplate = restTemplate;
    }

    @KafkaHandler
    public void consume(WorkerLaunch message) {
        log.info(String.format("$$ -> Consumed Message -> %s",message));
        log.warn("starting worker");
        final String s = operationService.launchWorker();
        log.warn(s);
    }


    @KafkaHandler
    public void consumeResult(ResultSchema message) {
        log.info(String.format("$$ -> Consumed Message -> %s", message));
        log.warn("result worker");
    }


    @KafkaHandler
    public void consumeError(WorkerFailure message) {
        log.info(String.format("$$ -> Consumed Message -> %s", message));

        try {
            final ResponseEntity<WorkerFailure> responseEntityResponseEntity = restTemplate.postForEntity("http://localhost:8086/send", message, WorkerFailure.class);
            log.info(responseEntityResponseEntity.toString());
        } catch (ResourceAccessException e) {
            log.error("unable to send notification to notification service " + e.getMessage());
            log.warn("following msg was not sent to notification service : " + message);
            throw new CommonMasterServiceException("unable to send notification to notification service ", e);
        }
    }
}