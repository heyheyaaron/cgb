import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class MyTest {
    @Test
    public void test1(){
        System.out.println(LocalDateTime.now().getDayOfMonth()==LocalDateTime.now().getDayOfMonth());
        System.out.println(LocalDateTime.now().getDayOfYear());
        System.out.println(LocalDateTime.now().getDayOfWeek());

    }
}
