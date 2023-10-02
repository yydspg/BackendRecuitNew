package com.pg.backend.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "backend.jwt")
public class JwtProperties {
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;
}
