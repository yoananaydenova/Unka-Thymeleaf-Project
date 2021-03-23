package com.yoanan.unka.model.service;

import org.springframework.web.multipart.MultipartFile;

public class LessonAddServiceModel {

    private String title;
    private String description;
    private MultipartFile img;
    private String videoUrl;
    private Long courseId;

    public LessonAddServiceModel() {
    }

    public String getTitle() {
        return title;
    }

    public LessonAddServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LessonAddServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getImg() {
        return img;
    }

    public LessonAddServiceModel setImg(MultipartFile img) {
        this.img = img;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public LessonAddServiceModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public Long getCourseId() {
        return courseId;
    }

    public LessonAddServiceModel setCourseId(Long courseId) {
        this.courseId = courseId;
        return this;
    }
}
