package com.yoanan.unka.model.binding;

import javax.validation.constraints.Size;

public class CourseAddBindingModel {

    private String name;
    private String description;

    public CourseAddBindingModel() {
    }

    @Size(min=2, message = "Name of the course must be minimum 2 characters!")
    public String getName() {
        return name;
    }

    public CourseAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @Size(min=2, message = "Description of the course must be minimum 2 characters!")
    public String getDescription() {
        return description;
    }

    public CourseAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
