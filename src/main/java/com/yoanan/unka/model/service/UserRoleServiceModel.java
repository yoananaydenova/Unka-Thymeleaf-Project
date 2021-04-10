package com.yoanan.unka.model.service;

import com.yoanan.unka.model.entity.enums.UserRole;

public class UserRoleServiceModel extends BaseServiceModel{

    private UserRole role;

    public UserRoleServiceModel() {
    }

    public UserRoleServiceModel(UserRole role) {
        this.role = role;
    }

    public UserRole getRole() {
        return role;
    }

    public UserRoleServiceModel setRole(UserRole role) {
        this.role = role;
        return this;
    }
}
