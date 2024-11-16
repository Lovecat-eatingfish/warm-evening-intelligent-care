package com.innovation.warm.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 32782
 */
@Component
@ConfigurationProperties(prefix = "warm.jwt")
@Data
public class JwtProperties {

    private String secret;
    private long expireTime;
    private String headTokenKey;
}
