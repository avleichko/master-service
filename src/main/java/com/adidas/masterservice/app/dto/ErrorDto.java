package com.adidas.masterservice.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data @AllArgsConstructor
public class ErrorDto {
    Date date;
    String excptionDetails;
}
