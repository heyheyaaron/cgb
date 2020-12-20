package com.example.domain.zuoxi.bean;

import lombok.Data;

@Data
public class Group extends AbstractPersistable{
    private String groupLeader;
    private String groupName;
}
