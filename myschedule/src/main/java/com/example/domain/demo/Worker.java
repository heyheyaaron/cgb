//package com.example.domain.demo;
//
//import com.example.domain.zuoxi.bean.Shift;
//
//import java.util.Collection;
//import java.util.HashMap;
//
//public class Worker {
//	private Integer type;
//	private String name;
//	private Integer maxDay = 0;
//	private Integer easyDay;
//	private HashMap<Integer, Shift> shifts = new HashMap<>(30);
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String n) {
//		name = n;
//	}
//
//	public Worker() {
//	}
//
//	public Worker(int i, String n) {
//		type = i;
//		name = n;
//	}
//
//	public Integer getType() {
//		return type;
//	}
//
//	public void setType(Integer i) {
//		type = i;
//	}
//
//	public Integer getEasyDay() {
//		return easyDay;
//	}
//
//	public void setEasyDay(Integer i) {
//		easyDay = i;
//	}
//
//	public Integer getMaxDay() {
//		return maxDay;
//	}
//
//	/**
//	 * 添加班次，并计算最大连续工作天数
//	 * @param s
//	 */
//	public void addShift(Shift s) {
//		shifts.put(s.getWorkDate().getDay(), s);
//		easyDay--;
//		int m = 0;
//		for (int i = 1; i <= 31; i++) {
//			if (shifts.containsKey(i)) {
//				m++;
//				maxDay = Math.max(maxDay, m);
//			} else {
//				m = 0;
//			}
//		}
//	}
//
//	public Integer getShiftTotal() {
//		return shifts.size();
//	}
//
//	public Collection<Shift> getShifts() {
//		return shifts.values();
//	}
//}