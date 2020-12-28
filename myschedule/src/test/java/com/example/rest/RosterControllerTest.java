package com.example.rest;

import com.example.controller.zuoxi.RosterController;
import com.example.domain.zuoxi.bean.Employee;
import com.example.domain.zuoxi.bean.EmployeeAvailability;
import com.example.domain.zuoxi.bean.Roster;
import com.example.domain.zuoxi.bean.Shift;
import com.example.enums.AvailabilityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {
        "optaplanner.solver.termination.spent-limit=5m", // Effectively disable this termination in favor of the best-score-limit
        "optaplanner.solver.termination.best-score-limit=0init/0hard/*soft",
        "logging.level.org.optaplanner=debug"})
public class RosterControllerTest {
    @Autowired
    private RosterController rosterController;
    private static List<String> shiftTypeTemplateList = new ArrayList();
    public static List<Employee> employeeTemplateList = new ArrayList();
    public static List<EmployeeAvailability> employeeAvailabilityTemplateList = new ArrayList();

    @Test
    @Timeout(600_000)
    public void solve() {
        Roster problem = testGenerate();
        LocalDateTime start = LocalDateTime.now();
        System.out.println(start+"=======start");
        Roster solution = rosterController.solve(problem);
        LocalDateTime end = LocalDateTime.now();
        System.out.println(end+"=======end");
        System.out.println("运行了"+Duration.between(start,end).getSeconds()+"秒");
        assertTrue(!solution.getShiftList().stream().anyMatch(s -> Objects.isNull(s.getEmployeeId())));
        assertTrue(solution.getHardSoftScore().isFeasible());
    }
    public Roster testGenerate(){
        int empNum=160;
        List<Shift> shiftTemplate = getShiftTemplate(12,130);
        List<Long> employeeTemplate = getEmployeeTemplate(empNum);
        Roster roster = new Roster(1L,employeeTemplate,shiftTemplate,employeeTemplateList,employeeAvailabilityTemplateList);
        return roster;
    }
    public List<Shift> getShiftTemplate(int month,int dailyShiftTimes){
        AtomicLong index=new AtomicLong(0);
        LocalDate localDate = LocalDate.now();
        int length = localDate.lengthOfMonth();
        Random r = new Random();
        List<Shift> list= new ArrayList<>();
        for (int day = 1; day <= length; day++) {
            for (int i = 0; i < dailyShiftTimes; i++) {
                int hour = r.nextInt(13);
                LocalDateTime start = LocalDateTime.of(2020, month, day, hour, 00);
                LocalDateTime end = LocalDateTime.of(2020, month, day, hour+8, 00);
                String shiftType = shiftTypeTemplateList.get(r.nextInt(shiftTypeTemplateList.size()));
                list.add(new Shift(index.getAndIncrement(),shiftType, start, end, r.nextInt(3)));
            }
        }
        return list;
    }
    public List<Long> getEmployeeTemplate(int num){
        List<Long> employees=new ArrayList<>();
        for (int i = 0; i <num ; i++) {
            employees.add((long)i);
        }
        return employees;
    }
    static {
        shiftTypeTemplateList.addAll(Arrays.asList("A1","A2","A3","P1","P2","P3"));
        Employee employee1 = new Employee(1L,"aaa","123456","zhuguan1",3,"5");
        Employee employee2 = new Employee(2L,"bbb","123222","zhuguan1",3,"5");
        Employee employee3= new Employee(3L,"ccc","123433","zhuguan1",3,"5");
        Employee employee4= new Employee(4L,"ddd","123444","zhuguan2",4,"4");
        Employee employee5= new Employee(5L,"eee","123455","zhuguan3",4,"3");
        Employee employee6= new Employee(6L,"fff","123466","zhuguan3",5,"3");
        Employee employee7= new Employee(7L,"ggg","123467","zhuguan2",5,"4");
        Employee employee8= new Employee(8L,"hhh","123468","zhuguan2",5,"4");
        Employee employee9= new Employee(9L,"eee","123455","zhuguan3",4,"3");
        Employee employee10= new Employee(10L,"fff","123466","zhuguan3",5,"3");
        employeeTemplateList = Arrays.asList(employee1, employee2, employee3, employee4,employee5,employee6,employee7,employee8,employee9,employee10);
        EmployeeAvailability employeeAvailability1 = new EmployeeAvailability(1L,1L, LocalDateTime.of(2020, 12, 1, 0, 00)
                , LocalDateTime.of(2020, 12, 3, 0, 00), AvailabilityType.UNAVAILABLE.getType());
        employeeAvailabilityTemplateList.add(employeeAvailability1);
    }
}