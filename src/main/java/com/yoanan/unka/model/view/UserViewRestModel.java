package com.yoanan.unka.model.view;


import java.util.ArrayList;
import java.util.List;

public class UserViewRestModel extends BaseViewModel{

    private String username;
    private String fullName;
    private List<Long> roleId = new ArrayList<>();

    public UserViewRestModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserViewRestModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserViewRestModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public List<Long> getRoleId() {
        return roleId;
    }

    public UserViewRestModel setRoleId(List<Long> roleId) {
        this.roleId = roleId;
        return this;
    }
}
