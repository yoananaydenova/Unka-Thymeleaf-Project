package com.yoanan.unka.model.view;

public class UserRoleViewModel extends BaseViewModel {

    private String role;

    public UserRoleViewModel() {
    }

    public String getRole() {
        return role;
    }

    public UserRoleViewModel setRole(String role) {
        this.role = role;
        return this;
    }
}
