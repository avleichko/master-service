package com.adidas.masterservice.app.services;

import com.adidas.masterservice.app.dto.Brand;
import com.adidas.masterservice.app.dto.MigrationFlow;
import com.adidas.masterservice.app.dto.MigrationType;
import com.adidas.masterservice.app.dto.WorkerStarterDto;
import com.adidas.masterservice.app.properties.AdidasLocales;
import com.adidas.masterservice.app.properties.ReebokLocales;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.task.launcher.TaskLaunchRequest;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class OperationServiceImpl implements OperationService {

    private final Source source;


    private final KafaProducer kafaProducer;


    private final AdidasLocales adidasLocales;


    private final ReebokLocales reebokLocales;

    @Autowired
    public OperationServiceImpl(Source source, KafaProducer kafaProducer, AdidasLocales adidasLocales, ReebokLocales reebokLocales) {
        this.source = source;
        this.kafaProducer = kafaProducer;
        this.adidasLocales = adidasLocales;
        this.reebokLocales = reebokLocales;
    }

//TODO leave it for time when spring cloud task will be relevant again

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

    @Scheduled(cron = "${olap.full.feed.gen.schedule}")
    @Override
    public void launchWorkerOlapic() {
        log.warn("OLAPIC full feed started");
        WorkerStarterDto  workerStarterDto = new WorkerStarterDto();
        Map<String, String> locales = adidasLocales.getLocales();
        final Map<String, String> localesReebok = reebokLocales.getLocales();
        locales.putAll(localesReebok);

        locales.forEach((key, value) -> {
            workerStarterDto.setLocale(value.toString());
            if (localesReebok.containsKey(key)){
                workerStarterDto.setBrand(Brand.REBOK);
            }else{
                workerStarterDto.setBrand(Brand.ADIDAS);
            }
            workerStarterDto.setFlow(MigrationFlow.FULL);
            workerStarterDto.setMigrationType(MigrationType.OLAPIC);
            run(workerStarterDto);
        });
        log.warn("OLAPIC full feed ended");
    }

    @Scheduled(cron = "${bv.full.feed.gen.schedule}")
    @Override
    public void launchWorkerBV() {
        log.warn("BV full feed started");
        WorkerStarterDto  workerStarterDto = new WorkerStarterDto();

        workerStarterDto.setFlow(MigrationFlow.FULL);
        workerStarterDto.setMigrationType(MigrationType.BAZAAR_VOCE);

        for (Brand value : Brand.values()) {
            workerStarterDto.setBrand(value);
            run(workerStarterDto);
        }
        log.warn("BV full feed ended");
    }

    @Override
    public void run(WorkerStarterDto workerStarterDto) {
        log.warn("event sending start");
        workerStarterDto.setUuid(UUID.randomUUID().toString());
        kafaProducer.sendMessage(toRequestJobMessage(workerStarterDto));
        log.warn("event sending end");
    }


    private String toRequestJobMessage(WorkerStarterDto workerStarterDto) {
        return "run job locale "
                + workerStarterDto.getLocale()
                + "; brand_code "
                + workerStarterDto.getBrand().getBrandCode()
                + "; type inline "
                + "; start " + workerStarterDto.getStartDate()
                + "; end " + workerStarterDto.getEndDate()
                + ";";
    }
}
