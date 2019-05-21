package com.adidas.masterservice.app.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RestartService {
    @Autowired
    private RestartEndpoint restartEndpoint;

    public void restartApp() {
        log.warn("trying to restart service");
        restartEndpoint.restart();
    }
}

