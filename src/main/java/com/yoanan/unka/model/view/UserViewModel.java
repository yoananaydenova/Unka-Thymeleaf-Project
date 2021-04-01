package com.yoanan.unka.model.view;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends BaseViewModel{

    private String username;
    private String fullName;
    private String email;
    private List<CourseViewModel> teachCourses = new ArrayList<>();
    private List<Long> roleId = new ArrayList<>();

    public UserViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserViewModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<CourseViewModel> getTeachCourses() {
        return teachCourses;
    }

    public UserViewModel setTeachCourses(List<CourseViewModel> teachCourses) {
        this.teachCourses = teachCourses;
        return this;
    }

    public List<Long> getRoleId() {
        return roleId;
    }

    public UserViewModel setRoleId(List<Long> roleId) {
        this.roleId = roleId;
        return this;
    }
}
