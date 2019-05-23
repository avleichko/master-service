package com.adidas.masterservice.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor
public class WorkerStarterDto {
    private final String uuid =  UUID.randomUUID().toString();
    private String locale;
    private Brand brand;
    private MigrationFlow flow;
    private MigrationType migrationType;

}
