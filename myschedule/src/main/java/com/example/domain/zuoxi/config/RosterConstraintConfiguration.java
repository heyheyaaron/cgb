package com.example.domain.zuoxi.config;

import lombok.Data;
import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration;
import org.optaplanner.core.api.domain.constraintweight.ConstraintWeight;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
@Data
@ConstraintConfiguration(constraintPackage = "solver")
public class RosterConstraintConfiguration {
    @ConstraintWeight("one employee can not work in the same day")
    private HardSoftScore sameDayConflict = HardSoftScore.ofHard(13);
    @ConstraintWeight("Break between non-consecutive shifts is at least 12 hours")
    private HardSoftScore breakAtLeast12Hours = HardSoftScore.ofHard(7);
}
