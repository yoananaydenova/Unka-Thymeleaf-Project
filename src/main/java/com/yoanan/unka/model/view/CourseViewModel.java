package com.yoanan.unka.model.view;

public class CourseViewModel extends BaseViewModel {

    private String name;
    private String imgUrl;
    private String description;

    public CourseViewModel() {
    }

    public String getName() {
        return name;
    }

    public CourseViewModel setName(String name) {
        this.name = name;
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
}
