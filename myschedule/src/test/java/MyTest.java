import com.example.domain.zuoxi.bean.EmployeeAvailability;
import com.example.domain.zuoxi.bean.GroupPlan;
import com.example.domain.zuoxi.bean.Roster;
import com.example.domain.zuoxi.bean.Shift;
import com.example.enums.AvailabilityType;
import com.example.rest.RosterControllerTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.optaplanner.core.api.score.ScoreExplanation;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.constraint.ConstraintMatchTotal;
import org.optaplanner.core.api.score.constraint.Indictment;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.solver.SolverManagerConfig;
import org.optaplanner.test.impl.score.buildin.hardsoft.HardSoftScoreVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MyTest {
    private HardSoftScoreVerifier<Roster> scoreVerifier = new HardSoftScoreVerifier<>(
            SolverFactory.createFromXmlResource(
                    "solverConfig.xml"));

    @Test
    @Timeout(600_000)
    public void solveRoster1() throws ExecutionException, InterruptedException {
        List<Long> employees = Arrays.asList(1L, 2L,3L,4L);
        Shift shift0 = new Shift(0L,"F1", LocalDateTime.of(2020,12,2,9,00),
                LocalDateTime.of(2020,12,2,18,00),LocalDate.of(2020,12,2),1);
        Shift shift1 = new Shift(1L,"A3", LocalDateTime.of(2020,12,2,00,00),
                LocalDateTime.of(2020,12,2,18,00),LocalDate.of(2020,12,2),1);
        Shift shift2 = new Shift(2L,"A2", LocalDateTime.of(2020,12,2,00,00),
                LocalDateTime.of(2020,12,2,19,00),LocalDate.of(2020,12,2),1);
        Shift shift3 = new Shift(3L,"A3", LocalDateTime.of(2020,12,2,11,00),
                LocalDateTime.of(2020,12,2,18,00),LocalDate.of(2020,12,2),2);
        List<Shift> shifts = Arrays.asList(shift0,shift1,shift2,shift3);
        //scoreVerifier.assertHardWeight("one employee can not work in the same day",0,problem);
        shift0.setEmployeeId(1L);
        //shift0.setPinned(true);
        shift1.setEmployeeId(2L);
        //shift1.setPinned(true);
        shift2.setEmployeeId(3L);
        //shift2.setPinned(true);
        shift3.setEmployeeId(4L);
        EmployeeAvailability employeeAvailability1 = new EmployeeAvailability(1L,1L, LocalDate.of(2020, 12, 1)
                , AvailabilityType.UNDESIRED.getType());
        List<EmployeeAvailability> employeeAvailabilities = new ArrayList<>();
        employeeAvailabilities.add(employeeAvailability1);
        List<GroupPlan> groupPlanTemplate = getGroupPlanTemplate();
        Roster problem=new Roster(3L,employees,shifts, RosterControllerTest.employeeTemplateList,employeeAvailabilities,groupPlanTemplate);
        //Roster solution = rosterController.solve(problem);
        //assertTrue(solution.getHardSoftScore().isFeasible());
        //scoreVerifier.assertSoftWeight("same team over 3 shift in one day",0,problem);
        org.optaplanner.core.config.solver.SolverConfig solverConfig = org.optaplanner.core.config.solver.SolverConfig.createFromXmlResource("solverConfig.xml");
        SolverManager<Roster, String> solverManager = SolverManager.create(solverConfig, new SolverManagerConfig());
        SolverJob<Roster, String> solve = solverManager.solve("1", problem);
        Roster finalBestSolution = solve.getFinalBestSolution();
        ScoreManager<Roster, HardSoftScore> scoreManager = ScoreManager.create(SolverFactory.createFromXmlResource("solverConfig.xml"));
        ScoreExplanation<Roster, HardSoftScore> explanation = scoreManager.explainScore(finalBestSolution);
        Map<Object, Indictment<HardSoftScore>> indictmentMap = explanation.getIndictmentMap();
        Map<String, ConstraintMatchTotal<HardSoftScore>> constraintMatchTotalMap = explanation.getConstraintMatchTotalMap();
        scoreVerifier.assertHardWeight("same team over 3 shift in one day",0,problem);
        System.out.println("0!!!!");
    }
    private List<GroupPlan> getGroupPlanTemplate() {
        List<GroupPlan> list= new ArrayList<>();
        GroupPlan plan1=new GroupPlan(1L,1L,"A", LocalDate.of(2020,12,1));
        GroupPlan plan2=new GroupPlan(2L,1L,"P",LocalDate.of(2020,12,2));
        list.add(plan1);
        list.add(plan2);
        return list;
    }

}
