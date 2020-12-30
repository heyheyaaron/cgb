package com.example.domain.zuoxi.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


/**
 * 组计划表，记录每天该组轮哪类型的班
 * from 用户设置
 */
@Data
@AllArgsConstructor
public class GroupPlan extends AbstractPersistable{
    private Long groupId;
    //如A、P
    private String shiftType;
    private LocalDate date;

    public GroupPlan(long id, Long groupId, String shiftType, LocalDate date) {
        super(id);
        this.groupId = groupId;
        this.shiftType = shiftType;
        this.date = date;
    }
    public boolean checkType(String shiftName){
        System.out.println("checking"+shiftName);
        return !shiftName.startsWith(shiftType);
    }
}
