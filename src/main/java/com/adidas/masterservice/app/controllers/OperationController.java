package com.adidas.masterservice.app.controllers;

import com.adidas.masterservice.app.dto.WorkerStarterDto;
import com.adidas.masterservice.app.services.KafaProducer;
import com.adidas.masterservice.app.services.OperationService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationController {
    @Autowired
    private OperationService operationService;

    @Autowired
    KafaProducer kafaProducer;

    @Autowired
    MeterRegistry meterRegistry;

    @PostMapping("/run")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void runMigration(@RequestBody WorkerStarterDto workerStarterDto){
        kafaProducer.sendMessage(workerStarterDto.toString());

        meterRegistry.counter(workerStarterDto.toString(), Tags.empty()).increment(1);
    }
}
