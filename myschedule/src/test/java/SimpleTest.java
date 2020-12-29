import com.example.domain.zuoxi.bean.Group;
import com.example.domain.zuoxi.bean.Shift;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimpleTest {
    private static List<Shift> shiftTemplateList = new ArrayList();
    private static List<String> shiftTypeTemplateList = new ArrayList();

    @Test
    public void test1(){
        LocalDate localDate = LocalDate.now();
        LocalDate localDate1 = localDate.plusDays(1);
        System.out.println(localDate1);
        Random r = new Random();
        Duration between = Duration.between(LocalDateTime.of(2020,12,10,8,00),LocalDateTime.of(2020,12,10,18,00));
        System.out.println(between.toMinutes());
        Group group = new Group();
        group.setIndex(3);
        group.setShiftTypePattern("PPAAA");
        group.calculateShiftType();
        group.calculateShiftType();
        int i =5;
        System.out.println(i/2);
    }
    @Test
    public void test2(){
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
        System.out.println(shift0.getStartTime().toLocalDate().plusDays(1));
        System.out.println(shift1.getStartTime().toLocalDate());
        System.out.println(shift2.getStartTime().toLocalDate());

    }
}
