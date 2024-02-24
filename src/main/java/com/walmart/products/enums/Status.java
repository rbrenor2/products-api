package com.walmart.products.enums;
public enum Status {
    CREATED("CREATED"),
    IN_ANALYSIS("IN ANALYSIS"),
    REJECTED("REJECTED"),
    APPROVED("APPROVED");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}