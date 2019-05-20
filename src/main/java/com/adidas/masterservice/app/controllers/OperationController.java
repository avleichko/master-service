package com.adidas.masterservice.app.controllers;

import com.adidas.masterservice.app.dto.WorkerStarterDto;
import com.adidas.masterservice.app.services.KafaProducer;
import com.adidas.masterservice.app.services.OperationService;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OperationController {

    private final OperationService operationService;

    private final KafaProducer kafaProducer;

    private final MeterRegistry meterRegistry;

    @Autowired
    public OperationController(OperationService operationService, KafaProducer kafaProducer, MeterRegistry meterRegistry) {
        this.operationService = operationService;
        this.kafaProducer = kafaProducer;
        this.meterRegistry = meterRegistry;
    }

    @PostMapping(value = "/run", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void runMigration(@RequestBody WorkerStarterDto workerStarterDto){
        log.info("starting job with following params: "+ workerStarterDto);
        operationService.run(workerStarterDto);
    }
}
