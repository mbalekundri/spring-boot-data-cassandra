package com.bezkoder.spring.data.cassandra.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "retry.cassandra")
@Getter
@Setter
public class RetryConfiguration {
    private int dbErrorMaxRetryCount;
    private int dbMaxInterval;
}
