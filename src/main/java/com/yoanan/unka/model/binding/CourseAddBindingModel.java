package com.yoanan.unka.model.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;


public class CourseAddBindingModel {

    private String name;
    private String description;
    private MultipartFile img;

    public CourseAddBindingModel() {
    }

    @NotEmpty
    @Length(min=2, message = "Name of the course must be minimum 2 characters!")
    public String getName() {
        return name;
    }

    public CourseAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @NotEmpty
    @Length(min=2, message = "Description of the course must be minimum 2 characters!")
    public String getDescription() {
        return description;
    }

    public CourseAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getImg() {
        return img;
    }

    public CourseAddBindingModel setImg(MultipartFile img) {
        this.img = img;
        return this;
    }
}
