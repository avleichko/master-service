package com.adidas.masterservice.app.services;

import com.adidas.masterservice.app.dto.WorkerStarterDto;
import org.springframework.cloud.stream.messaging.Source;

public interface OperationService {
    String launchWorker();

    void run(WorkerStarterDto workerStarterDto);

    void launchWorkerBV();

    void launchWorkerOlapic();
}
