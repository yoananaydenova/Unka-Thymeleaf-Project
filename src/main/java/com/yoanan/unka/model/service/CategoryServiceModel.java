package com.yoanan.unka.model.service;

import java.util.HashSet;
import java.util.Set;

public class CategoryServiceModel extends BaseServiceModel{

    private String name;
    private String description;
    private Set<CourseServiceModel> courses = new HashSet<>();

    public CategoryServiceModel() {
    }

    public String getName() {
        return name;
    }

    public CategoryServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<CourseServiceModel> getCourses() {
        return courses;
    }

    public CategoryServiceModel setCourses(Set<CourseServiceModel> courses) {
        this.courses = courses;
        return this;
    }
}
