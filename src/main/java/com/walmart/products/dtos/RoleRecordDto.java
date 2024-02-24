package com.walmart.products.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

// It could also be a normal Java Class
// Record class type is available in Java 16
// - Record comes with
    // - Getters, toString(), they are immutable, private and final by default
public record RoleRecordDto(@NotBlank  String name) {

}
