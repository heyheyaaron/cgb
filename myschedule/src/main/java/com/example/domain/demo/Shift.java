//package com.example.domain.demo;
//
//public class Shift implements Comparable<Shift> {
//	private Integer no;
//	private WorkDate workDate;
//	private Worker driver;
//	private Worker assistant1;
//	private Worker assistant2;
//
//	public Shift() {
//	}
//
//	public Shift(int i) {
//		no = i;
//	}
//
//	public Integer getNo() {
//		return no;
//	}
//
//	public void setNo(Integer i) {
//		no = i;
//	}
//
//	public WorkDate getWorkDate() {
//		return workDate;
//	}
//
//	public void setWorkDate(WorkDate d) {
//		workDate = d;
//	}
//
//	public Worker getDriver() {
//		return driver;
//	}
//
//	public void setDriver(Worker w) {
//		driver = w;
//	}
//
//	public Worker getAssistant1() {
//		return assistant1;
//	}
//
//	public void setAssistant1(Worker w) {
//		assistant1 = w;
//	}
//
//	public Worker getAssistant2() {
//		return assistant2;
//	}
//
//	public void setAssistant2(Worker w) {
//		assistant2 = w;
//	}
//
//	/**
//	 * 当前班次是否已有该woker
//	 * @param w
//	 * @return
//	 */
//	public boolean containsWorker(Worker w) {
//		return driver == w || assistant1 == w || assistant2 == w;
//	}
//
//	public boolean isDone() {
//		return driver != null && assistant1 != null && assistant2 != null;
//	}
//
//	@Override
//	public int compareTo(Shift shift) {
//		int a = this.getWorkDate().getDay() * 10 + no;
//		int b = shift.getWorkDate().getDay() * 10 + shift.getNo();
//		return a - b;
//	}
//}