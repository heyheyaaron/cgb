//package com.example.domain.demo;
//
//import com.example.domain.zuoxi.bean.Shift;
//
//import java.util.Collection;
//import java.util.HashMap;
//
//public class WorkDate {
//	private Integer day;
//	private HashMap<Integer, Shift> shifts = new HashMap<>(7);
//
//	public WorkDate(int i) {
//		this();
//		day = i;
//	}
//
//	/**
//	 *	 创建3个班次
//	 */
//	public WorkDate() {
//		for (int i = 1; i <= 3; i++) {
//			this.addShift(new Shift(i));
//		}
//	}
//
//	public Integer getDay() {
//		return day;
//	}
//
//	public void setDay(Integer d) {
//		day = d;
//	}
//
//	/**
//	 * 	占用一个班次
//	 * @param s
//	 */
//	public void addShift(Shift s) {
//		s.setWorkDate(this);
//		shifts.put(s.getNo(), s);
//	}
//
//	public Collection<Shift> getShifts() {
//		return shifts.values();
//	}
//
//	/**
//	 * 	当前日历是否已经有worker
//	 * @param w
//	 * @return
//	 */
//	public boolean containsWorker(Worker w) {
//		for (Shift s : this.getShifts()) {
//			if (s.containsWorker(w)) {
//				return true;
//			}
//		}
//		return false;
//	}
//}
