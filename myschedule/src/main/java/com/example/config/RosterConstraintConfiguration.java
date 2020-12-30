package com.example.config;

import lombok.Data;
import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration;
import org.optaplanner.core.api.domain.constraintweight.ConstraintWeight;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
@Data
@ConstraintConfiguration(constraintPackage = "solver")
public class RosterConstraintConfiguration {
    @ConstraintWeight("one employee can not work in the same day")
    private HardSoftScore sameDayConflict = HardSoftScore.ofHard(9);
    @ConstraintWeight("Break between non-consecutive shifts is at least 12 hours")
    private HardSoftScore breakAtLeast12Hours = HardSoftScore.ofHard(7);
    @ConstraintWeight("at most work 22 days")
    private HardSoftScore atMostWork22Days=HardSoftScore.ofHard(11);
    @ConstraintWeight("Unavailable time slot for an employee")
    private HardSoftScore unavailable=HardSoftScore.ofHard(5);
    @ConstraintWeight("No more than 3 consecutive shifts")
    private HardSoftScore noMore3consecutiveShifts=HardSoftScore.ofHard(5);
    @ConstraintWeight("Desired time slot for an employee")
    private HardSoftScore desiredTime=HardSoftScore.ofSoft(100);
    @ConstraintWeight("Undesired time slot for an employee")
    private HardSoftScore undesiredTime=HardSoftScore.ofSoft(100);
    @ConstraintWeight("shift type error")
    private HardSoftScore shiftTypeErroe=HardSoftScore.ofHard(13);
    @ConstraintWeight("same team need same shift in one day")
    private HardSoftScore sameTeamNeedSameShift=HardSoftScore.ofHard(1);
    @ConstraintWeight("overnight need rest for 2 days")
    private HardSoftScore overnightNeedRest2Days=HardSoftScore.ofHard(9);
    @ConstraintWeight("no rest work rest")
    private HardSoftScore noRestWorkRest=HardSoftScore.ofSoft(80);
}
