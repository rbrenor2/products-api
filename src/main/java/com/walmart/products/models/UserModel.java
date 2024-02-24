package com.walmart.products.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="users")
public class UserModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private @NotBlank RoleModel roleId;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private BranchModel branchId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleModel getRoleId() {
        return roleId;
    }

    public void setRoleId(@NotBlank RoleModel roleId) {
        this.roleId = roleId;
    }

    public BranchModel getBranchId() {
        return branchId;
    }

    public void setBranchId(@NotBlank BranchModel branchId) {
        this.branchId = branchId;
    }
}
