package com.adidas.masterservice.app.controllers;

import com.adidas.masterservice.app.dto.WorkerStarterDto;
import com.adidas.masterservice.app.services.KafaProducer;
import com.adidas.masterservice.app.services.OperationService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public void runMigration(@RequestBody WorkerStarterDto workerStarterDto){
        kafaProducer.sendMessageError(workerStarterDto.toString());

        //meterRegistry.gauge("kafka-error-adidas-summary",Tags.of("asd",message),errorKafka.get(message));
        meterRegistry.counter(workerStarterDto.toString(), Tags.empty()).increment(1);
    }
}
