import com.example.domain.zuoxi.bean.Employee;
import com.example.domain.zuoxi.bean.EmployeeAvailability;
import com.example.domain.zuoxi.bean.Roster;
import com.example.domain.zuoxi.bean.Shift;
import com.example.enums.AvailabilityType;
import com.example.rest.RosterControllerTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.test.impl.score.buildin.hardsoft.HardSoftScoreVerifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyTest {
    private HardSoftScoreVerifier<Roster> scoreVerifier = new HardSoftScoreVerifier<>(
            SolverFactory.createFromXmlResource(
                    "solverConfig.xml"));

    @Test
    @Timeout(600_000)
    public void solveRoster1() {
        //Roster problem = generateRosterProblem();
        Employee employee1 = new Employee(1L,"aaa","123456","zhuguan1",3,"5");
        Employee employee2 = new Employee(2L,"bbb","123222","zhuguan1",3,"5");
        List<Long> employees = Arrays.asList(1L, 2L);
        Shift shift0 = new Shift(0L,"A1", LocalDateTime.of(2020,12,1,9,00),LocalDateTime.of(2020,12,1,18,00),3);
        Shift shift1 = new Shift(1L,"A1", LocalDateTime.of(2020,12,2,9,00),LocalDateTime.of(2020,12,2,18,00),3);
        Shift shift2 = new Shift(2L,"A2", LocalDateTime.of(2020,12,3,10,00),LocalDateTime.of(2020,12,3,19,00),4);
        Shift shift3 = new Shift(3L,"A3", LocalDateTime.of(2020,12,13,11,00),LocalDateTime.of(2020,12,13,18,00),5);
        List<Shift> shifts = Arrays.asList(shift0,shift1,shift2,shift3);
        //scoreVerifier.assertHardWeight("one employee can not work in the same day",0,problem);
        shift0.setEmployeeId(1L);
        shift1.setEmployeeId(1L);
        shift2.setEmployeeId(1L);
        shift3.setEmployeeId(1L);
        EmployeeAvailability employeeAvailability1 = new EmployeeAvailability(1L,1L, LocalDateTime.of(2020, 12, 1, 0, 00)
                , LocalDateTime.of(2020, 12, 3, 0, 00), AvailabilityType.UNDESIRED.getType());
        List<EmployeeAvailability> employeeAvailabilities = new ArrayList<>();
        employeeAvailabilities.add(employeeAvailability1);
        Roster problem=new Roster(3L,employees,shifts, RosterControllerTest.employeeTemplateList,employeeAvailabilities);
        //Roster solution = rosterController.solve(problem);
        //assertTrue(solution.getHardSoftScore().isFeasible());
        //scoreVerifier.assertHardWeight("at most work 22 days",0,problem);
        scoreVerifier.assertSoftWeight("at most work 22 days",0,problem);
        System.out.println("0!!!!");
    }

}
