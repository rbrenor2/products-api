package com.walmart.products.dtos;

import com.walmart.products.models.BranchModel;
import com.walmart.products.models.RoleModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// It could also be a normal Java Class
// Record class type is available in Java 16
// - Record comes with
    // - Getters, toString(), they are immutable, private and final by default
public record UserRecordDto(@NotBlank String name, @NotBlank BranchModel branchId, @NotBlank RoleModel roleId) {

}
