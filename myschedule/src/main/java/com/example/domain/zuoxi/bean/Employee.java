package com.example.domain.zuoxi.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends AbstractPersistable {
    private String name;
    private String uid;
    private String superior;
    private int level;
    private String groupId;
    private LocalDateTime startWorkTime;
    private LocalDateTime endWorkTime;

    public Employee(long id, String name, String uid, String superior, int level, String groupId) {
        super(id);
        this.name = name;
        this.uid = uid;
        this.superior = superior;
        this.level = level;
        this.groupId = groupId;
    }
}
