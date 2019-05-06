package com.adidas.masterservice.app.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.task.launcher.TaskLaunchRequest;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OperationServiceImpl implements OperationService {


    @Autowired
    Source source;

    @Scheduled(cron = "${olap.full.feed.gen.schedule}")
    @Override
    public String launchWorker() {
        String url = "maven://com.adidas.task:worker:0.0.1-SNAPSHOT";
        TaskLaunchRequest taskLaunchRequest = new TaskLaunchRequest(url, null, null, null, "olapic-worker");

        for (int i = 0; i < 3; i++) {
            source.output().send(new GenericMessage<TaskLaunchRequest>(taskLaunchRequest));
            log.warn("worker:" + i + " started");
        }
        return "success";
    }
}
