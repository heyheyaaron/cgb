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
@Data
@PlanningEntity(pinningFilter = ShiftPinningFilter.class)
        //,difficultyComparatorClass = ShiftDifficultyComparator.class)
@AllArgsConstructor
@NoArgsConstructor
public class Shift extends AbstractPersistable {
    private String shiftName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate date;
    private int week;
    @PlanningVariable(valueRangeProviderRefs = "employeeRange")
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
}
