package com.example.domain.zuoxi.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 员工状态表
 * state由AvailabilityType枚举类指定
 * @Author: Aaron
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAvailability extends AbstractPersistable {
    private Long employeeId;
    private LocalDate date;
    private String state;
    private Long shiftId;

    public EmployeeAvailability(long id, Long employeeId, LocalDate date,String state) {
        super(id);
        this.employeeId = employeeId;
        this.date = date;
        this.state = state;
    }
    public EmployeeAvailability(long id, Long employeeId, LocalDate date, String state,Long shiftId) {
        super(id);
        this.employeeId = employeeId;
        this.date = date;
        this.state = state;
        this.shiftId=shiftId;
    }
}
