package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 2 * @Author: Aaron
 * 3 * @Date: 2021/1/8 17:08
 * 4
 */
@ConfigurationProperties(prefix = "cfg.drools")
@Component
@Data
public class DroolsParameterConfiguration {
    private int gapHours=12;
    private int workDays=22;
}
