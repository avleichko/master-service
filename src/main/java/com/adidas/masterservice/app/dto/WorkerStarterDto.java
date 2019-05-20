package com.adidas.masterservice.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor
public class WorkerStarterDto {
    private String uuid =  UUID.randomUUID().toString();
    private String locale;
    private Brand brand;
    // TODO rid of string representation of time
    private String startDate;
    private String endDate;
    MigrationFlow flow;
    MigrationType migrationType;

}
