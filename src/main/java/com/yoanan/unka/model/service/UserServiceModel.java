package com.yoanan.unka.model.service;

import com.yoanan.unka.model.view.CourseViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserServiceModel extends BaseServiceModel{

    private String username;
    private String fullName;
    private List<CourseServiceModel> teachCourses = new ArrayList<>();
    private List<UserRoleServiceModel>roles = new ArrayList<>();

    public UserServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public List<CourseServiceModel> getTeachCourses() {
        return teachCourses;
    }

    public UserServiceModel setTeachCourses(List<CourseServiceModel> teachCourses) {
        this.teachCourses = teachCourses;
        return this;
    }

    public List<UserRoleServiceModel> getRoles() {
        return roles;
    }

    public UserServiceModel setRoles(List<UserRoleServiceModel> roles) {
        this.roles = roles;
        return this;
    }
}
