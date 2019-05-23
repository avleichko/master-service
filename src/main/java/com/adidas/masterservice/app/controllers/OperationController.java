package com.adidas.masterservice.app.controllers;

import com.adidas.masterservice.app.dto.MigrationType;
import com.adidas.masterservice.app.dto.WorkerStarterDto;
import com.adidas.masterservice.app.services.OperationService;
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


    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping(value = "/run", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void runMigration(@RequestBody WorkerStarterDto workerStarterDto){
        log.info("starting job with following params: "+ workerStarterDto);

        operationService.run(workerStarterDto);
    }
}
