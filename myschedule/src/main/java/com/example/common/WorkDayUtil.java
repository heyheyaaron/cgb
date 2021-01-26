package com.example.common;

import java.util.Calendar;

/**
 * 2 * @Author: Aaron
 * 3 * @Date: 2021/1/26 11:12
 * 4
 */
public class WorkDayUtil {
    public static void main(String[] args) {
        System.out.println(countWorkDay(2021, 1));
    }
    /**
          * 获取指定年月有多少个工作日）
          *
          * @param year
          * @param month
          * @return
          */
    public static int countWorkDay(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        // 月份是从0开始计算，所以需要bai减1
        c.set(Calendar.MONTH, month - 1);
 
        // 当月最后一天的日期
        int max = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 开始日期为1号
        int start = 1;
        // 计数
        int count = 0;
        while (start <= max) {
            c.set(Calendar.DAY_OF_MONTH, start);
            if (isWorkDay(c)) {
                count++;
            }
            start++;
        }
        return count;
    }
 
    // 判断是否工作日（未排除法定节假日，由于涉及到农历节日，处理很麻烦）
    public static boolean isWorkDay(Calendar c) {
        // 获取星期,1~7,其中1代表星期日，2代表星期一 ... 7代表星期六
        int week = c.get(Calendar.DAY_OF_WEEK);
        // 不是周六和周日的都认为是工作日
        return week != Calendar.SUNDAY && week != Calendar.SATURDAY;
    }
}
