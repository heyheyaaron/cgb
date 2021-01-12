package com.example.config;

import lombok.Data;
import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration;
import org.optaplanner.core.api.domain.constraintweight.ConstraintWeight;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
@Data
@ConstraintConfiguration(constraintPackage = "solver")
public class RosterConstraintConfiguration {
    @ConstraintWeight("one employee can not work in the same day")
    private HardSoftScore sameDayConflict = HardSoftScore.ofHard(39);
    @ConstraintWeight("Break between non-consecutive shifts is at least 12 hours")
    private HardSoftScore breakAtLeast12Hours = HardSoftScore.ofHard(37);
    @ConstraintWeight("at most work 22 days")
    private HardSoftScore atMostWork22Days=HardSoftScore.ofHard(33);
    @ConstraintWeight("Unavailable time slot for an employee")
    private HardSoftScore unavailable=HardSoftScore.ofHard(5);
    @ConstraintWeight("No more than 6 consecutive shifts")
    private HardSoftScore noMore6consecutiveShifts=HardSoftScore.ofHard(37);
    @ConstraintWeight("Desired time slot for an employee")
    private HardSoftScore desiredTime=HardSoftScore.ofSoft(100);
    @ConstraintWeight("Undesired time slot for an employee")
    private HardSoftScore undesiredTime=HardSoftScore.ofSoft(100);
    @ConstraintWeight("shift type error")
    private HardSoftScore shiftTypeErroe=HardSoftScore.ofHard(13);
    @ConstraintWeight("same team need same shift in one day")
    private HardSoftScore sameTeamNeedSameShift=HardSoftScore.ofHard(3);
    @ConstraintWeight("same team over 3 shift in one day")
    private HardSoftScore sameTeamOver3Shift=HardSoftScore.ofHard(25);
//    @ConstraintWeight("same team need close shift in one day")
//    private HardSoftScore sameTeamNeedCloseShift=HardSoftScore.ofSoft(1);
    @ConstraintWeight("overnight need rest for 2 days")
    private HardSoftScore overnightNeedRest2Days=HardSoftScore.ofHard(19);
    @ConstraintWeight("no rest work rest")
    private HardSoftScore noRestWorkRest=HardSoftScore.ofSoft(80);
}
