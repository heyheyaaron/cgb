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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "optaplanner.solver.termination.spent-limit=3m", // Effectively disable this termination in favor of the best-score-limit
        "optaplanner.solver.termination.best-score-limit=0hard/*soft",
        "logging.level.org.optaplanner=info"})
public class TimeTableControllerTest {

    @Autowired
    private TimeTableController timeTableController;
    @Autowired
    private RosterController rosterController;

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
        List<Employee> employees = Arrays.asList(employee1, employee2, employee3, employee4,employee5);
                //, employee6,employee7,employee8);
        Shift shift0 = new Shift(0L,"A1", LocalDateTime.of(2020,12,10,9,00),LocalDateTime.of(2020,12,10,18,00),3);
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
        shift15.setEmployee(employee5);
        List<Shift> shifts = Arrays.asList(shift0,shift1,shift2,shift3,shift4,shift5,shift6,shift7,shift8,shift9,shift10,shift11,shift12,shift13,shift14,shift15);

        //List<Shift> shifts=Arrays.asList(shift1,shift6,shift11);
        return new Roster(2L,employees,shifts);
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

}