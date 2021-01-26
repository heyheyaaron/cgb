package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 2 * @Author: Aaron
 * 3 * @Date: 2021/1/8 17:08
 * 4
 */
@ConfigurationProperties(prefix = "cgb.drools")
@Component
@Data
public class DroolsParameterConfiguration {
    private int gapHours=12;
    private int workDays=22;
    private List<LocalDate> holidays=new ArrayList<>();
   public DroolsParameterConfiguration(){
       holidays.add(LocalDate.of(2021,2,11));
       holidays.add(LocalDate.of(2021,2,12));
       holidays.add(LocalDate.of(2021,2,13));
   }
}
