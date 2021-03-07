package com.yoanan.unka.model.service;

public class CourseServiceModel {

    private Long id;
    private String name;
    private String description;

    public CourseServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public CourseServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CourseServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CourseServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
