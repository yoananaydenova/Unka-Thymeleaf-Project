package com.yoanan.unka.model.view;

public class CourseNameViewModel extends BaseViewModel{
    private String name;

    public CourseNameViewModel() {
    }

    public String getName() {
        return name;
    }

    public CourseNameViewModel setName(String name) {
        this.name = name;
        return this;
    }

}
