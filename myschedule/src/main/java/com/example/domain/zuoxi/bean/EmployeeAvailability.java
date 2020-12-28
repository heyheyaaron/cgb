package com.example.domain.zuoxi.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAvailability extends AbstractPersistable {
    private Long employeeId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String state;
    private Long shiftId;

    public EmployeeAvailability(long id, Long employeeId, LocalDateTime startDateTime, LocalDateTime endDateTime, String state) {
        super(id);
        this.employeeId = employeeId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.state = state;
    }
    public EmployeeAvailability(long id, Long employeeId, LocalDateTime startDateTime, LocalDateTime endDateTime, String state,Long shiftId) {
        super(id);
        this.employeeId = employeeId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.state = state;
        this.shiftId=shiftId;
    }
}
