import com.example.domain.zuoxi.bean.Shift;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
        Random r = new Random(12345);
        for (int i = 0; i < 10; i++) {
            System.out.println(r.nextInt(100));
        }
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

    public static void main(String[] args) {
        KieServices kss = KieServices.Factory.get();
        KieContainer kc = kss.getKieClasspathContainer();
        KieSession ks =kc.newKieSession("session");
        int count = ks.fireAllRules();
        System.out.println("总执行了"+count+"条规则");
        ks.dispose();
    }
}
