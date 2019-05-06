package com.adidas.masterservice.app.services;

import org.springframework.cloud.stream.messaging.Source;

public interface OperationService {
    String launchWorker();
}
