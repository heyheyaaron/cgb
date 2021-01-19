package com.example.domain.zuoxi.bean;

import com.example.filter.ShiftPinningFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.entity.PlanningPin;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.validation.constraints.AssertTrue;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@PlanningEntity(pinningFilter = ShiftPinningFilter.class)
        //,difficultyComparatorClass = ShiftDifficultyComparator.class)
@AllArgsConstructor
@NoArgsConstructor
public class Shift extends AbstractPersistable {
    private String shiftName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    public LocalDate date;
    private int week;
    @PlanningVariable(valueRangeProviderRefs = "employeeRange",nullable = false)
    //,nullable = true)可为空，貌似没用
    private Long employeeId;
    @PlanningPin
    private boolean pinned;

    @AssertTrue(message = "Shift's end date time is not at least 30 minutes" +
            " after shift's start date time")
    public boolean isValid() {
        return startTime != null && endTime != null &&
                (Duration.between(startTime, endTime).getSeconds() / 60) >= 30;
    }

    public Shift(long id, String shiftName, LocalDateTime startTime, LocalDateTime endTime,LocalDate date, int week) {
        super(id);
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date=date;
        this.week = week;
    }
    public Shift(long id, String shiftName, LocalDateTime startTime, LocalDateTime endTime,LocalDate date, boolean pinned) {
        super(id);
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date=date;
        this.pinned=pinned;
    }
    public Shift(String shiftName, LocalDateTime startTime, LocalDateTime endTime, int week) {
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.week = week;
    }
    public Shift(long id, String shiftName, LocalDateTime startTime, LocalDateTime endTime, LocalDate date) {
        super(id);
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date=date;
    }


    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", shiftName='" + shiftName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", date=" + date +
                ", week=" + week +
                ", employeeId=" + employeeId +
                ", pinned=" + pinned +
                '}';
    }
    public boolean checkLast(){
        return !Objects.equals(date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth(),date.getDayOfMonth());
    }
    public int getDuration(LocalDateTime localDateTime){
        int until = (int) startTime.until(localDateTime, ChronoUnit.MINUTES);
        return until>=0?until:-until;
    }
    public static int getMaxDay(List<Shift> list){
        List<Shift> collect = list.stream().sorted(Comparator.comparing(Shift::getDate)).collect(Collectors.toList());
        System.out.println("调用了");
        if(collect.size()==0){
            return 0;
        }
        int m=1;
        int maxDay=1;
        for(int i = 0; i < collect.size()-1; i++) {
            if (collect.get(i).getDate().getDayOfMonth()+1==collect.get(i).getDate().getDayOfMonth()){
                m++;
                maxDay=m>maxDay?m:maxDay;
            }else {
                m=1;
            }
        }
        return maxDay;
    }
    public int getWorkDay(){
        if (shiftName.startsWith("E")){
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shift shift = (Shift) o;
        return id==shift.id&&week == shift.week &&
                pinned == shift.pinned &&
                Objects.equals(shiftName, shift.shiftName) &&
                Objects.equals(startTime, shift.startTime) &&
                Objects.equals(endTime, shift.endTime) &&
                Objects.equals(date, shift.date) &&
                Objects.equals(employeeId, shift.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,shiftName, startTime, endTime, date, week, employeeId, pinned);
    }

    public static void main(String[] args) {
        Shift shift= new Shift();
        shift.setStartTime(LocalDateTime.of(2021,1,5,6,0,0));
        int duration = shift.getDuration(LocalDateTime.now());
        System.out.println(duration);
    }
}
