package com.example.enums;

public enum AvailabilityType {
    UNAVAILABLE("UNAVAILABLE"),
    UNDESIRED("UNDESIRED"),
    DESIRED("DESIRED")
    ;

    private String type;

    public String getType() {
        return type;
    }

    AvailabilityType(String type) {
        this.type = type;
    }
}