import com.example.domain.zuoxi.bean.Group;
import com.example.domain.zuoxi.bean.Shift;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
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
        LocalDate now = LocalDate.now();
        int dayOfMonth = now.getDayOfMonth();
        System.out.println(dayOfMonth);
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        System.out.println(dayOfWeek);
        int length = now.getMonth().length(true);
        System.out.println(length);
        System.out.println(now.getMonth().maxLength());
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,4);
        System.out.println(weekFields);
        LocalDate with = now.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));
        System.out.println(with);
        String str = "abs";
        boolean a = str.startsWith("a");
        System.out.println(a);

    }
}
