package com.adidas.masterservice.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class WorkerStarterDto {
    private String locale;
    private Brand brand;
    // TODO rid of string representation of time
    private String startDate;
    private String endDate;
    MigrationFlow flow;
    MigrationType migrationType;

}
