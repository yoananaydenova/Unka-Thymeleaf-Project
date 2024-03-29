package com.yoanan.unka.model.service;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class CourseAddServiceModel {

    private String name;
    private BigDecimal price;
    private MultipartFile img;
    private String description;
    private String teacher;
    private Set<String> categories = new HashSet<>();

    public CourseAddServiceModel() {
    }

    public String getName() {
        return name;
    }

    public CourseAddServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CourseAddServiceModel setPrice(BigDecimal price) {
        this.price = price;
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

    public String getTeacher() {
        return teacher;
    }

    public CourseAddServiceModel setTeacher(String teacher) {
        this.teacher = teacher;
        return this;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public CourseAddServiceModel setCategories(Set<String> categories) {
        this.categories = categories;
        return this;
    }
}
