package com.example.domain.zuoxi.bean;

import com.example.config.RosterConstraintConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.constraintweight.ConstraintConfigurationProvider;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@Data
@PlanningSolution
@AllArgsConstructor
@NoArgsConstructor
public class Roster extends AbstractPersistable{
    @ConstraintConfigurationProvider
    private RosterConstraintConfiguration rosterConstraintConfiguration=new RosterConstraintConfiguration();
    @ProblemFactCollectionProperty
    private List<Employee> employees;
    @ProblemFactCollectionProperty
    private List<EmployeeAvailability> employeeAvailabilities;
    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "employeeRange")
    private List<Long> employeeIdList;
    @PlanningEntityCollectionProperty
    private List<Shift> shiftList;
    @PlanningScore
    private HardSoftScore hardSoftScore;

    public Roster(long id, List<Long> employeeIdList, List<Shift> shiftList,List<Employee> employees,List<EmployeeAvailability> employeeAvailabilities) {
        super(id);
        this.employeeIdList = employeeIdList;
        this.shiftList = shiftList;
        this.employees=employees;
        this.employeeAvailabilities=employeeAvailabilities;
    }
}
