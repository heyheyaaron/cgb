package com.example.domain.zuoxi.bean;

import com.example.domain.zuoxi.filter.ShiftPinningFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.entity.PlanningPin;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.validation.constraints.AssertTrue;
import java.time.Duration;
import java.time.LocalDateTime;
@Data
@PlanningEntity(pinningFilter = ShiftPinningFilter.class)
        //,difficultyComparatorClass = ShiftDifficultyComparator.class)
@AllArgsConstructor
@NoArgsConstructor
public class Shift extends AbstractPersistable {
    private String shiftType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int need;
    @PlanningVariable(valueRangeProviderRefs = "employeeRange")
    //,nullable = true)可为空，貌似没用
    private Employee employee;
    @PlanningPin
    private boolean pinned;

    @AssertTrue(message = "Shift's end date time is not at least 30 minutes" +
            " after shift's start date time")
    public boolean isValid() {
        return startTime != null && endTime != null &&
                (Duration.between(startTime, endTime).getSeconds() / 60) >= 30;
    }

    public Shift(long id, String shiftType, LocalDateTime startTime, LocalDateTime endTime,int need) {
        super(id);
        this.shiftType = shiftType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.need=need;
    }
    public Shift(long id, String shiftType, LocalDateTime startTime, LocalDateTime endTime,boolean pinned) {
        super(id);
        this.shiftType = shiftType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pinned=pinned;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", shiftType='" + shiftType + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", need=" + need +
                ", employee=" + employee +
                ", pinned=" + pinned +
                '}';
    }
}
