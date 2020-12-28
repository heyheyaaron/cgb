package com.example.rest;

import com.example.controller.TimeTableController;
import com.example.controller.zuoxi.RosterController;
import com.example.domain.Lesson;
import com.example.domain.Room;
import com.example.domain.TimeTable;
import com.example.domain.Timeslot;
import com.example.domain.zuoxi.bean.Employee;
import com.example.domain.zuoxi.bean.Roster;
import com.example.domain.zuoxi.bean.Shift;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "optaplanner.solver.termination.spent-limit=3m", // Effectively disable this termination in favor of the best-score-limit
        "optaplanner.solver.termination.best-score-limit=0init/0hard/*soft",
        "logging.level.org.optaplanner=debug"})
public class TimeTableControllerTest {

    @Autowired
    private TimeTableController timeTableController;
    @Autowired
    private RosterController rosterController;
    private static List<Shift> shiftTemplateList = new ArrayList();
    private static List<String> shiftTypeTemplateList = new ArrayList();

    @Test
    @Timeout(600_000)
    public void solve() {
        TimeTable problem = generateProblem();
        TimeTable solution = timeTableController.solve(problem);
        assertFalse(solution.getLessonList().isEmpty());
        for (Lesson lesson : solution.getLessonList()) {
            assertNotNull(lesson.getTimeslot());
            assertNotNull(lesson.getRoom());
        }
        assertTrue(solution.getHardSoftScore().isFeasible());
    }
    @Test
    @Timeout(600_000)
    public void solveRoster() {
        Roster problem = generateRosterProblem();
        Roster solution = rosterController.solve(problem);
        assertTrue(solution.getHardSoftScore().isFeasible());
        System.out.println("0");
    }
    @Test
    @Timeout(600_000)
    public void solveRosterBatch() {
        int empNum = 1600;
        int shiftNum=48;
        Roster problem = generateRosterProblemBatch(empNum,shiftNum);
        Roster solution = rosterController.solve(problem);
        assertTrue(solution.getHardSoftScore().isFeasible());
        System.out.println("0");
    }
    private Roster generateRosterProblemBatch(int empNum,int shiftNum){
        List<Long> employees=new ArrayList<>();
        for (int i = 0; i <empNum ; i++) {
            employees.add((long)i);
        }
        int times = 1;
        int left=shiftNum;
        int templateNum=shiftTemplateList.size();
        AtomicLong index=new AtomicLong(0);
        if (shiftNum>templateNum){
            times=shiftNum/shiftTemplateList.size();
            left=shiftNum%templateNum;
        }
        List<Shift> shifts = new ArrayList<>();
        for (int i = 0; i <times ; i++) {
            for (int j = 0; j <templateNum ; j++) {
                Shift shift= new Shift();
                Shift template = shiftTemplateList.get(j);
                shift.setId(index.getAndIncrement());
                shift.setStartTime(template.getStartTime());
                shift.setEndTime(template.getEndTime());
                shift.setShiftType(template.getShiftType());
                shift.setNeed(template.getNeed());
                shifts.add(shift);
            }
        }
        for (int i = 0; i <left ; i++) {
            Shift shift = shiftTemplateList.get(i);
            shift.setId(index.getAndIncrement());
            shifts.add(shift);
        }
//        for (int i = 0; i <shifts.size() ; i++) {
//            Shift shift = shifts.get(i);
//            shift.setId((long)i);
//        }
        return new Roster(4L,employees,shifts, RosterControllerTest.employeeTemplateList,RosterControllerTest.employeeAvailabilityTemplateList);
    }

    private Roster generateRosterProblem(){
        Employee employee1 = new Employee(1L,"aaa","123456","zhuguan1",3,"5");
        Employee employee2 = new Employee(2L,"bbb","123222","zhuguan1",3,"5");
        Employee employee3= new Employee(3L,"ccc","123433","zhuguan1",3,"5");
        Employee employee4= new Employee(4L,"ddd","123444","zhuguan2",4,"4");
        Employee employee5= new Employee(5L,"eee","123455","zhuguan3",4,"3");
        Employee employee6= new Employee(6L,"fff","123466","zhuguan2",5,"4");
        Employee employee7= new Employee(7L,"ggg","123467","zhuguan2",5,"4");
        Employee employee8= new Employee(8L,"hhh","123468","zhuguan2",5,"4");
        //List<Employee> employees = Arrays.asList(employee1, employee2, employee3, employee4,employee5);
                //, employee6,employee7,employee8);
        List<Long> employees = Arrays.asList(1L,2L,3L,4L,5L,6L,7L,8L,9L);
        Shift shift0 = new Shift(0L,"A1", LocalDateTime.of(2020,12,10,8,00),LocalDateTime.of(2020,12,10,18,00),3);
        Shift shift1 = new Shift(1L,"A1", LocalDateTime.of(2020,12,10,9,00),LocalDateTime.of(2020,12,10,18,00),3);
        Shift shift2 = new Shift(2L,"A2", LocalDateTime.of(2020,12,10,10,00),LocalDateTime.of(2020,12,10,19,00),4);
        Shift shift3 = new Shift(3L,"A3", LocalDateTime.of(2020,12,11,0,00),LocalDateTime.of(2020,12,11,18,00),5);
        Shift shift4 = new Shift(4L,"P1", LocalDateTime.of(2020,12,11,14,00),LocalDateTime.of(2020,12,12,18,00),3);
        Shift shift5 = new Shift(5L,"P2", LocalDateTime.of(2020,12,13,15,00),LocalDateTime.of(2020,12,13,18,00),4);
        Shift shift6 = new Shift(6L,"A1", LocalDateTime.of(2020,12,10,9,00),LocalDateTime.of(2020,12,10,18,00),3);
        Shift shift7 = new Shift(7L,"A2", LocalDateTime.of(2020,12,10,10,00),LocalDateTime.of(2020,12,10,19,00),4);
        Shift shift8 = new Shift(8L,"A3", LocalDateTime.of(2020,12,11,0,00),LocalDateTime.of(2020,12,11,18,00),5);
        Shift shift9 = new Shift(9L,"P1", LocalDateTime.of(2020,12,11,14,00),LocalDateTime.of(2020,12,12,18,00),3);
        Shift shift10= new Shift(10L,"P2", LocalDateTime.of(2020,12,13,15,00),LocalDateTime.of(2020,12,13,18,00),4);
        Shift shift11 = new Shift(11L,"A1", LocalDateTime.of(2020,12,10,9,00),LocalDateTime.of(2020,12,10,18,00),3);
        Shift shift12 = new Shift(12L,"A2", LocalDateTime.of(2020,12,10,10,00),LocalDateTime.of(2020,12,10,19,00),4);
        Shift shift13 = new Shift(13L,"A3", LocalDateTime.of(2020,12,11,0,00),LocalDateTime.of(2020,12,11,18,00),5);
        Shift shift14 = new Shift(14L,"P1", LocalDateTime.of(2020,12,11,14,00),LocalDateTime.of(2020,12,12,18,00),3);
        Shift shift15= new Shift(15L,"P2", LocalDateTime.of(2020,12,13,15,00),LocalDateTime.of(2020,12,13,18,00),true);
        shift15.setEmployeeId(5L);
        List<Shift> shifts = Arrays.asList(shift0,shift1,shift2,shift3,shift4,shift5,shift6,shift7,shift8,shift9,shift10,shift11,shift12,shift13,shift14,shift15);

        //List<Shift> shifts=Arrays.asList(shift1,shift6,shift11);
        return new Roster(4L,employees,shifts, RosterControllerTest.employeeTemplateList,RosterControllerTest.employeeAvailabilityTemplateList);
    }

    private TimeTable generateProblem() {
        List<Timeslot> timeslotList = new ArrayList<>();
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
//        timeslotList.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
//        timeslotList.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
//        timeslotList.add(new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));

        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room("Room A"));
        roomList.add(new Room("Room B"));
        roomList.add(new Room("Room C"));

        List<Lesson> lessonList = new ArrayList<>();
        lessonList.add(new Lesson(101L, "Math", "B. May", "9th grade"));
        lessonList.add(new Lesson(102L, "Physics", "M. Curie", "9th grade"));
        lessonList.add(new Lesson(103L, "Geography", "M. Polo", "9th grade"));
        lessonList.add(new Lesson(104L, "English", "I. Jones", "9th grade"));
        lessonList.add(new Lesson(105L, "Spanish", "P. Cruz", "8th grade"));

        lessonList.add(new Lesson(201L, "Math", "B. May", "10th grade"));
        lessonList.add(new Lesson(202L, "Chemistry", "M. Curie", "10th grade"));
        lessonList.add(new Lesson(203L, "History", "I. Jones", "10th grade"));
        lessonList.add(new Lesson(204L, "English", "P. Cruz", "10th grade",new Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)),new Room("Room B"),true));
        lessonList.add(new Lesson(205L, "French", "M. Curie", "8th grade"));
        return new TimeTable(lessonList, roomList,timeslotList);
    }
    static {
        Shift shift0 = new Shift("A1", LocalDateTime.of(2020,12,10,8,00),LocalDateTime.of(2020,12,10,18,00),3);
        Shift shift1 = new Shift("A1", LocalDateTime.of(2020,12,10,9,00),LocalDateTime.of(2020,12,10,18,00),3);
        Shift shift2 = new Shift("A2", LocalDateTime.of(2020,12,10,10,00),LocalDateTime.of(2020,12,10,19,00),4);
        Shift shift3 = new Shift("A3", LocalDateTime.of(2020,12,11,0,00),LocalDateTime.of(2020,12,11,18,00),5);
        Shift shift4 = new Shift("P1", LocalDateTime.of(2020,12,11,14,00),LocalDateTime.of(2020,12,12,18,00),3);
        shiftTemplateList.addAll(Arrays.asList(shift0,shift1,shift2,shift3,shift4));
        shiftTypeTemplateList.addAll(Arrays.asList("A1","A2","A3","P1","P2","P3"));
        //Pair pair = Pair.of()
    }
    protected List<Shift> getShiftTemplate(int month){
        LocalDate localDate = LocalDate.now();
        int length = localDate.lengthOfMonth();
        Random r = new Random();
        List<Shift> list= new ArrayList<>();
        for (int day = 1; day <= length; day++) {
            int hour = r.nextInt(13);
            LocalDateTime start = LocalDateTime.of(2020, month, day, hour, 00);
            LocalDateTime end = LocalDateTime.of(2020, month, day, hour+8, 00);
            String shiftType = shiftTypeTemplateList.get(r.nextInt(shiftTypeTemplateList.size()));
            list.add(new Shift(shiftType,start,end,8));
        }
        return list;
    }
}