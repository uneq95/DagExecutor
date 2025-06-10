package com.ritesh.dag.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "dag")
public class DagProperties {
    private int corePoolSize = 10;
    private int maxPoolSize = 20;
    private int queueCapacity = 500;
}