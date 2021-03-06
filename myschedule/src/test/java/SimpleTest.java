import com.example.convert.ShiftConverter;
import com.example.domain.excel.ShiftBO;
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

public class SimpleTest {
    private static List<Shift> shiftTemplateList = new ArrayList();
    private static List<String> shiftTypeTemplateList = new ArrayList();

    @Test
    public void test1(){
        ShiftBO bo = new ShiftBO(null, 3, LocalDate.now());
        ShiftBO bo1 = new ShiftBO("A2", 3, LocalDate.now());
        ShiftBO bo2 = new ShiftBO("A3", 3, LocalDate.now());
        List<ShiftBO> boList = new ArrayList<>();
        boList.add(bo);
        boList.add(bo1);
        boList.add(bo2);
        ShiftConverter instance = ShiftConverter.INSTANCE;
        System.out.println(instance.bolist2dolist(boList));
        Shift shift = instance.bo2do(bo);
        System.out.println(shift);
        List<Integer> list=new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        Long distinct = list.stream().filter(a -> a != 2).distinct().count();
        System.out.println(distinct);
        System.out.println(575/31);
        System.out.println(575%31);
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
