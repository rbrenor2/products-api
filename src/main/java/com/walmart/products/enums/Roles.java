package com.walmart.products.enums;
public enum Roles {
    ANALYST("Analyst"),
    MANAGER("Manager");

    private final String value;

    Roles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}