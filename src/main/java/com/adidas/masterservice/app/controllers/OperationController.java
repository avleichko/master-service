package com.adidas.masterservice.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.task.launcher.TaskLaunchRequest;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@EnableBinding(Source.class)
@Slf4j
public class OperationController {

    @Value("${msg:hello world}")
    private String msg;

    @Autowired
    private Source source;

    @GetMapping("/msg")
    public String getmsg(){
        return msg;
    }



    @GetMapping("/olapic")
    public String olapic (){

        String  url ="maven://com.adidas.task:worker:0.0.1-SNAPSHOT";
        TaskLaunchRequest taskLaunchRequest = new TaskLaunchRequest(url, null, null, null, "olapic-worker");

        for(int  i=0; i< 10; i++) {
            source.output().send(new GenericMessage<TaskLaunchRequest>(taskLaunchRequest));
            log.warn("worker:"+ i +" started");
        }
        return "success";
    }


    @GetMapping("/workerStart")
    public String workerStart(){
        log.warn("worker start");
        return "started";
    }
    @GetMapping("/workerEnd")
    public String workerEnd(){
        log.warn("worker end");
        return "end";
    }
}
