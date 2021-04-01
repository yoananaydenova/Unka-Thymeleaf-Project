package com.yoanan.unka.model.service;

public class UserRoleServiceModel extends BaseServiceModel{
    private String role;

    public UserRoleServiceModel() {
    }

    public String getRole() {
        return role;
    }

    public UserRoleServiceModel setRole(String role) {
        this.role = role;
        return this;
    }
}
