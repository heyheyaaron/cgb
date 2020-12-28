package com.example.enums;

public enum ShiftType {
    A1("A1"),
    A2("A2"),
    A3("A3"),
    P1("P1"),
    P2("P2"),
    P3("P3"),
    F1("F1")
    ;

    private String type;

    public String getType() {
        return type;
    }

    ShiftType(String type) {
        this.type = type;
    }
}
