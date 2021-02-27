package com.yoanan.unka.model.entity;

import com.yoanan.unka.model.entity.enums.UserRole;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class UserRoleEntity extends BaseEntity{

    private UserRole role;

    public UserRoleEntity() {
    }

    @Enumerated(EnumType.STRING)
    public UserRole getRole() {
        return role;
    }

    public UserRoleEntity setRole(UserRole role) {
        this.role = role;
        return this;
    }
}
