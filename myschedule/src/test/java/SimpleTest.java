import com.example.domain.zuoxi.bean.Shift;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleTest {
    private static List<Shift> shiftTemplateList = new ArrayList();
    private static List<String> shiftTypeTemplateList = new ArrayList();

    @Test
    public void test1(){
        LocalDate localDate = LocalDate.now();
        localDate.plusDays(1);
        System.out.println(localDate);
        localDate.plus(1, ChronoUnit.DAYS);
        System.out.println(localDate);
        int length = localDate.lengthOfMonth();
        System.out.println(length+"天");
        Random r = new Random();
        Duration between = Duration.between(LocalDateTime.of(2020,12,10,8,00),LocalDateTime.of(2020,12,10,18,00));
        System.out.println(between.toMinutes());
    }

}
