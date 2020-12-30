package com.example.common;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Data
public class WeekData{
        // 一周的开始时间
        private LocalDate start;
        // 一周的结束时间
        private LocalDate end;

        public WeekData(List<LocalDate> localDates) {
            this.start = localDates.get(0);
            this.end = localDates.get(localDates.size()-1);
        }

        @Override
        public String toString() {
            return "开始时间：" + this.start + "，结束时间：" + this.end;
        }

    public static Map<Integer, WeekData> weeks(YearMonth yearMonth){
        LocalDate start = LocalDate.now().with(yearMonth).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = LocalDate.now().with(yearMonth).with(TemporalAdjusters.lastDayOfMonth());

        Map<Integer, WeekData> map = Stream.iterate(start, localDate -> localDate.plusDays(1l))
                .limit(ChronoUnit.DAYS.between(start, end)+1)
                .collect(Collectors.groupingBy(localDate -> localDate.get(WeekFields.of(DayOfWeek.SUNDAY, 1).weekOfMonth()),
                        Collectors.collectingAndThen(Collectors.toList(), WeekData::new)));
        return map;
    }
}