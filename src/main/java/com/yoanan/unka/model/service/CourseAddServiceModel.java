package com.yoanan.unka.model.service;

import org.springframework.web.multipart.MultipartFile;

public class CourseAddServiceModel{

    private String name;
    private MultipartFile img;
    private String description;

    public CourseAddServiceModel() {
    }

    public String getName() {
        return name;
    }

    public CourseAddServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public MultipartFile getImg() {
        return img;
    }

    public CourseAddServiceModel setImg(MultipartFile img) {
        this.img = img;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CourseAddServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
