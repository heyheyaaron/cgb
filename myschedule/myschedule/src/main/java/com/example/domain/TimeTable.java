package com.example.domain;

import lombok.Data;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;
@Data
@PlanningSolution
public class TimeTable {
    @PlanningEntityCollectionProperty
    private List<Lesson> lessonList;

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id="roomRange")
    private List<Room> roomList;

    @ValueRangeProvider(id="timeslotRange")
    @ProblemFactCollectionProperty
    private List<Timeslot> timeslot;

    @PlanningScore
    private HardSoftScore hardSoftScore;

    public TimeTable() {
    }

    public TimeTable(List<Lesson> lessonList, List<Room> roomList, List<Timeslot> timeslot) {
        this.lessonList = lessonList;
        this.roomList = roomList;
        this.timeslot = timeslot;
    }

}
