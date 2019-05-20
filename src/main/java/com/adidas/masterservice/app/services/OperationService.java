package com.adidas.masterservice.app.services;

import com.adidas.masterservice.app.dto.WorkerStarterDto;

public interface OperationService {
    String launchWorker();

    void run(WorkerStarterDto workerStarterDto);

    void launchWorkerBV();

    void launchWorkerOlapic();

    void launchWorkerInventory();
}
