package com.example.rest;

import com.example.common.WeekData;
import com.example.controller.zuoxi.RosterController;
import com.example.domain.zuoxi.bean.*;
import com.example.enums.AvailabilityType;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {
        "optaplanner.solver.termination.spent-limit=30m", // Effectively disable this termination in favor of the best-score-limit
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
        List<Shift> shiftTemplate = getShiftTemplate(12,95);
        Pair<List<Long>, List<Employee>> pair = getEmployeeTemplate(empNum);
        List<Long> employeeIds = pair.getLeft();
        List<Employee> employees = pair.getRight();
        //Map<Integer, WeekData> weeks = weeks(YearMonth.now());
        List<GroupPlan> groupPlans = getGroupPlanTemplate();
        Roster roster = new Roster(1L,employeeIds,shiftTemplate,employees,employeeAvailabilityTemplateList,groupPlans);
        return roster;
    }
    @Test
    public void testTeminate(){
        String problemId = "6f3b7bf9-1591-4c1c-9249-8712581461d0";
        rosterController.terminate(problemId);
    }
    @Test
    public void testStatus(){
        String problemId = "6f3b7bf9-1591-4c1c-9249-8712581461d0";
        System.out.println(rosterController.getSolverStatus(problemId).name());
    }

    private List<GroupPlan> getGroupPlanTemplate() {
        List<GroupPlan> list= new ArrayList<>();
        GroupPlan plan1=new GroupPlan(1L,1L,"A",LocalDate.of(2020,12,1));
        GroupPlan plan2=new GroupPlan(2L,1L,"P",LocalDate.of(2020,12,2));
        list.add(plan1);
        list.add(plan2);
        return list;
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
                list.add(new Shift(index.getAndIncrement(),shiftType, start, end, LocalDate.of(2020,month,day)));
            }
        }
        return list;
    }
    public Pair<List<Long>, List<Employee>> getEmployeeTemplate(int num){
        List<Long> employeeIds=new ArrayList<>();
        List<Employee> employees= new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i <num ; i++) {
            employeeIds.add((long)i);
            Employee employee = new Employee();
            employee.setId((long)i);
            employee.setName("emp"+i);
            employee.setGroupId((long)r.nextInt(num>10?num/10:1));
            employee.setLevel(r.nextInt(5));
            employees.add(employee);
        }
        Pair<List<Long>, List<Employee>> pair = Pair.of(employeeIds, employees);
        return pair;
    }
    static {
        shiftTypeTemplateList.addAll(Arrays.asList("A1","A2","A3","P1","P2","P3"));
        Employee employee1 = new Employee(1L,"aaa","123456","zhuguan1",3,1L);
        Employee employee2 = new Employee(2L,"bbb","123222","zhuguan1",3,1L);
        Employee employee3= new Employee(3L,"ccc","123433","zhuguan1",3,1L);
        Employee employee4= new Employee(4L,"ddd","123444","zhuguan2",4,1L);
        Employee employee5= new Employee(5L,"eee","123455","zhuguan3",4,3L);
        Employee employee6= new Employee(6L,"fff","123466","zhuguan3",5,3L);
        Employee employee7= new Employee(7L,"ggg","123467","zhuguan2",5,4L);
        Employee employee8= new Employee(8L,"hhh","123468","zhuguan2",5,4L);
        Employee employee9= new Employee(9L,"iii","123455","zhuguan3",4,3L);
        Employee employee10= new Employee(10L,"jjj","123466","zhuguan3",5,3L);
        employeeTemplateList = Arrays.asList(employee1, employee2, employee3, employee4,employee5,employee6,employee7,employee8,employee9,employee10);
        EmployeeAvailability employeeAvailability1 = new EmployeeAvailability(1L,1L, LocalDate.of(2020, 12, 1)
                , AvailabilityType.UNAVAILABLE.getType());
        EmployeeAvailability employeeAvailability2 = new EmployeeAvailability(2L,1L, LocalDate.of(2020, 12, 1)
                , AvailabilityType.UNDESIRED.getType());
        employeeAvailabilityTemplateList.add(employeeAvailability1);
        employeeAvailabilityTemplateList.add(employeeAvailability2);
    }
    public Map<Integer, WeekData> weeks(YearMonth yearMonth){
        LocalDate start = LocalDate.now().with(yearMonth).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = LocalDate.now().with(yearMonth).with(TemporalAdjusters.lastDayOfMonth());

        Map<Integer, WeekData> map = Stream.iterate(start, localDate -> localDate.plusDays(1l))
                .limit(ChronoUnit.DAYS.between(start, end)+1)
                .collect(Collectors.groupingBy(localDate -> localDate.get(WeekFields.of(DayOfWeek.SUNDAY, 1).weekOfMonth()),
                        Collectors.collectingAndThen(Collectors.toList(), WeekData::new)));
        return map;
    }
}