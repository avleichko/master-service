package com.adidas.masterservice.app.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Brand {
    ADIDAS("MDT_BRAND_v_11"),
    REBOK("MDT_BRAND_v_26");

    private String brandCode;
}
