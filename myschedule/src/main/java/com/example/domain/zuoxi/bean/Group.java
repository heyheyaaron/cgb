package com.example.domain.zuoxi.bean;

import lombok.Data;

@Data
public class Group extends AbstractPersistable{
    private String groupLeader;
    private String groupName;
    private String shiftType;
    //如PPAAA
    private String shiftTypePattern="PPAAA";
    //0-4
    private int index=0;

    public void calculateShiftType(){
        if (index<this.shiftTypePattern.length()-1){
            index=index+1;
        }else {
            index=0;
        }
        String shiftType = String.valueOf(this.shiftTypePattern.charAt(index));
        this.shiftType=shiftType;
        System.out.println(this);
    }
}
