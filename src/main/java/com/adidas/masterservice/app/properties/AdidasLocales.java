package com.adidas.masterservice.app.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "adidas")
@Data  @AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class AdidasLocales {
    private Map<String, String> locales = new HashMap<>();
}
