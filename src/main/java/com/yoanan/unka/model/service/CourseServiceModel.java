package com.yoanan.unka.model.service;

import java.math.BigDecimal;

public class CourseServiceModel extends BaseServiceModel {


    private String name;
    private BigDecimal price;
    private String imgUrl;
    private String teacher;
    private String description;

    private boolean isInShoppingCart;
    private boolean isEnrolled;
    private boolean isTeacherOfCourse;

    public CourseServiceModel() {
    }

    public String getName() {
        return name;
    }

    public CourseServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CourseServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public CourseServiceModel setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getTeacher() {
        return teacher;
    }

    public CourseServiceModel setTeacher(String teacher) {
        this.teacher = teacher;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CourseServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isInShoppingCart() {
        return isInShoppingCart;
    }

    public CourseServiceModel setInShoppingCart(boolean inShoppingCart) {
        isInShoppingCart = inShoppingCart;
        return this;
    }

    public boolean isEnrolled() {
        return isEnrolled;
    }

    public CourseServiceModel setEnrolled(boolean enrolled) {
        isEnrolled = enrolled;
        return this;
    }

    public boolean isTeacherOfCourse() {
        return isTeacherOfCourse;
    }

    public CourseServiceModel setTeacherOfCourse(boolean teacherOfCourse) {
        isTeacherOfCourse = teacherOfCourse;
        return this;
    }
}
