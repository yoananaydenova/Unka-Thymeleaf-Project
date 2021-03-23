package com.yoanan.unka.model.service;


import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.entity.ExerciseEntity;

import java.util.Set;

public class LessonServiceModel extends BaseServiceModel{

    private CourseServiceModel course;
    private String title;
    private String description;
    private String imgUrl;
    private String videoUrl;


    public LessonServiceModel() {
    }

    public CourseServiceModel getCourse() {
        return course;
    }

    public LessonServiceModel setCourse(CourseServiceModel course) {
        this.course = course;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public LessonServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LessonServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public LessonServiceModel setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public LessonServiceModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }
}
