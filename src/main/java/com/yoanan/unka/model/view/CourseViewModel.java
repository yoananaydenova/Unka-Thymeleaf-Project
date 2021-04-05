package com.yoanan.unka.model.view;

public class CourseViewModel extends BaseViewModel {

    private String name;
    private String price;
    private String imgUrl;
    private String description;

    private UserViewModel teacher;

    private boolean isInShoppingCart;
    private boolean isEnrolled;
    private boolean isTeacherOfCourse;


    public CourseViewModel() {
    }

    public UserViewModel getTeacher() {
        return teacher;
    }

    public CourseViewModel setTeacher(UserViewModel teacher) {
        this.teacher = teacher;
        return this;
    }

    public String getName() {
        return name;
    }

    public CourseViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public CourseViewModel setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public CourseViewModel setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CourseViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isInShoppingCart() {
        return isInShoppingCart;
    }

    public CourseViewModel setInShoppingCart(boolean inShoppingCart) {
        isInShoppingCart = inShoppingCart;
        return this;
    }

    public boolean isEnrolled() {
        return isEnrolled;
    }

    public CourseViewModel setEnrolled(boolean enrolled) {
        isEnrolled = enrolled;
        return this;
    }

    public boolean isTeacherOfCourse() {
        return isTeacherOfCourse;
    }

    public CourseViewModel setTeacherOfCourse(boolean teacherOfCourse) {
        isTeacherOfCourse = teacherOfCourse;
        return this;
    }
}
