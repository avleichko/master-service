package com.adidas.masterservice.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class WorkerStarterDto {
    private String locale;
    private Brand brand;
    private Date startDate;
    private Date endDate;
    MigrationFlow flow;
    MigrationType migrationType;

}
