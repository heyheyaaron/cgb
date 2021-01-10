package com.example.controller.zuoxi;

import com.example.config.DroolsParameterConfiguration;
import com.example.domain.zuoxi.bean.*;
import com.example.enums.AvailabilityType;
import org.apache.commons.lang3.tuple.Pair;
import org.optaplanner.core.api.score.ScoreExplanation;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.constraint.ConstraintMatch;
import org.optaplanner.core.api.score.constraint.ConstraintMatchTotal;
import org.optaplanner.core.api.score.constraint.Indictment;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/roster")
public class RosterController {
    @Resource(name="rosterSolverManager")
    private SolverManager solverManager;
    @Resource(name="rosterScoreManager")
    private ScoreManager scoreManager;
    @Autowired
    private DroolsParameterConfiguration configuration;
    private static List<String> shiftTypeTemplateList = new ArrayList();
    public static List<Employee> employeeTemplateList = new ArrayList();
    public static List<EmployeeAvailability> employeeAvailabilityTemplateList = new ArrayList();

    @GetMapping("/solveTest")
    public Roster solveTest() {
        Roster problem = testGenerate();
//        SolverConfig solverConfig = SolverConfig.createFromXmlResource("solverConfig.xml"); @RequestBody Roster problem
//        SolverManager<Roster, UUID> solverManager = SolverManager.create(solverConfig, new SolverManagerConfig());
//        ScoreManager<Roster, HardSoftScore> scoreManager = ScoreManager.create(SolverFactory.createFromXmlResource("solverConfig.xml"));
        String problemId = UUID.randomUUID().toString();
        System.out.println(problemId);
        // Submit the problem to start solving
        SolverJob<Roster, String> solverJob = solverManager.solve(problemId, problem);
        Roster solution;
        try {
            SolverStatus solverStatus = getSolverStatus(problemId);
            System.out.println(solverStatus);
            // Wait until the solving ends
            solution = solverJob.getFinalBestSolution();
            /*Map<Long, Long> collect = solution.getShiftList().stream().collect(Collectors.groupingBy(s -> s.getEmployeeId(), Collectors.counting()));
            collect.forEach((k,v)->{
                System.out.println("k="+k+",v="+v);
            });*/
            ScoreExplanation<Roster, HardSoftScore> scoreExplanation = scoreManager.explainScore(solution);
            String summary = scoreExplanation.getSummary();
            HardSoftScore score = scoreExplanation.getScore();
            Map<String, ConstraintMatchTotal<HardSoftScore>> constraintMatchTotalMap = scoreExplanation.getConstraintMatchTotalMap();
            Map<Object, Indictment<HardSoftScore>> indictmentMap = scoreExplanation.getIndictmentMap();
            //Roster solution1 = scoreExplanation.getSolution();
            //获取违反的约束和分数
            getConstrainNameAndScore(solution, indictmentMap);
            //System.out.println(solution1);
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solving failed.", e);
        }
        return solution;
    }
    @PostMapping("/solve")
    public Roster solve(@RequestBody Roster problem) {
//        SolverConfig solverConfig = SolverConfig.createFromXmlResource("solverConfig.xml"); @RequestBody Roster problem
//        SolverManager<Roster, UUID> solverManager = SolverManager.create(solverConfig, new SolverManagerConfig());
//        ScoreManager<Roster, HardSoftScore> scoreManager = ScoreManager.create(SolverFactory.createFromXmlResource("solverConfig.xml"));
        String problemId = UUID.randomUUID().toString();
        System.out.println(problemId);
        problem.setConfiguration(configuration);
        // Submit the problem to start solving
        SolverJob<Roster, String> solverJob = solverManager.solve(problemId, problem);
        Roster solution;
        try {
            SolverStatus solverStatus = getSolverStatus(problemId);
            System.out.println(solverStatus);
            // Wait until the solving ends
            solution = solverJob.getFinalBestSolution();
            System.out.println(solution.getShiftList().stream().filter(shift -> shift.getEmployeeId() == null).count());
            System.out.println(solution.getShiftList().stream().filter(shift -> shift.getEmployeeId() != null).count());
            /*Map<Long, Long> collect = solution.getShiftList().stream().collect(Collectors.groupingBy(s -> s.getEmployeeId(), Collectors.counting()));
            collect.forEach((k,v)->{
                System.out.println("k="+k+",v="+v);
            });*/
            ScoreExplanation<Roster, HardSoftScore> scoreExplanation = scoreManager.explainScore(solution);
            String summary = scoreExplanation.getSummary();
            HardSoftScore score = scoreExplanation.getScore();
            Map<String, ConstraintMatchTotal<HardSoftScore>> constraintMatchTotalMap = scoreExplanation.getConstraintMatchTotalMap();
            Map<Object, Indictment<HardSoftScore>> indictmentMap = scoreExplanation.getIndictmentMap();
            //Roster solution1 = scoreExplanation.getSolution();
            //获取违反的约束和分数
            getConstrainNameAndScore(solution, indictmentMap);
            //System.out.println(solution1);
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solving failed.", e);
        }
        return solution;
    }

    private void getConstrainNameAndScore(Roster solution, Map<Object, Indictment<HardSoftScore>> indictmentMap) {
        for (Shift shift : solution.getShiftList()) {
            Indictment<HardSoftScore> indictment = indictmentMap.get(shift);
            if (indictment == null) {
                continue;
            }
            // The score impact of that planning entity
            HardSoftScore totalScore = indictment.getScore();

            for (ConstraintMatch<HardSoftScore> constraintMatch : indictment.getConstraintMatchSet()) {
                String constraintName = constraintMatch.getConstraintName();
                HardSoftScore hardSoftScore = constraintMatch.getScore();
                System.out.println(shift+constraintName+":"+hardSoftScore);
            }
        }
    }

    @GetMapping("/terminate/{problemId}")
    public void terminate(@PathVariable String problemId) {
        System.out.println("手动结束");
        solverManager.terminateEarly(problemId);
    }
    @GetMapping("/getStatus/{problemId}")
    public SolverStatus getSolverStatus(@PathVariable String problemId) {
        //origin_parameter
        System.out.println("获取状态");
        return solverManager.getSolverStatus(problemId);
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
    @PostConstruct
    public void init(){
        shiftTypeTemplateList.addAll(Arrays.asList("A1","A2","A3","P1","P2","P3"));
        Employee employee1 = new Employee(1L,"aaa","123456","zhuguan1",3,1L);
        Employee employee2 = new Employee(2L,"bbb","123222","zhuguan1",3,1L);
        Employee employee3= new Employee(3L,"ccc","123433","zhuguan1",3,1L);
        Employee employee4= new Employee(4L,"ddd","123444","zhuguan2",4,4L);
        Employee employee5= new Employee(5L,"eee","123455","zhuguan3",4,3L);
        Employee employee6= new Employee(6L,"fff","123466","zhuguan3",5,3L);
        Employee employee7= new Employee(7L,"ggg","123467","zhuguan2",5,4L);
        Employee employee8= new Employee(8L,"hhh","123468","zhuguan2",5,4L);
        Employee employee9= new Employee(9L,"iii","123455","zhuguan3",4,3L);
        Employee employee10= new Employee(10L,"jjj","123466","zhuguan3",5,3L);
        employeeTemplateList = Arrays.asList(employee1, employee2, employee3, employee4,employee5,employee6,employee7,employee8,employee9,employee10);
        EmployeeAvailability employeeAvailability1 = new EmployeeAvailability(1L,1L, LocalDateTime.of(2020, 12, 1, 0, 00)
                , LocalDateTime.of(2020, 12, 3, 0, 00), AvailabilityType.UNAVAILABLE.getType());
        EmployeeAvailability employeeAvailability2 = new EmployeeAvailability(2L,1L, LocalDateTime.of(2020, 12, 1, 0, 00)
                , LocalDateTime.of(2020, 12, 3, 0, 00), AvailabilityType.UNDESIRED.getType());
        employeeAvailabilityTemplateList.add(employeeAvailability1);
        employeeAvailabilityTemplateList.add(employeeAvailability2);
    }
}