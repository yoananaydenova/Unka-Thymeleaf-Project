package com.yoanan.unka.model.service;

import org.springframework.web.multipart.MultipartFile;

public class CourseServiceModel extends BaseServiceModel {


    private String name;
    private String imgUrl;
    private String description;

    public CourseServiceModel() {
    }

    public String getName() {
        return name;
    }

    public CourseServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public CourseServiceModel setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
