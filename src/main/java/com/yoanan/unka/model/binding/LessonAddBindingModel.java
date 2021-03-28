package com.yoanan.unka.model.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LessonAddBindingModel {

    private String title;
    private String description;
    private MultipartFile img;
    private String videoUrl;
    private Long courseId;

    public LessonAddBindingModel() {
    }

    @NotEmpty(message = "Заглавието е зъдължително!")
    @Length(min = 3, max=30, message = "Заглавието на урока трябва да бъде от 3 до 30 символа!")
    public String getTitle() {
        return title;
    }

    public LessonAddBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    @Length(max = 500, message = "Описанието на курса трябва да е до 500 символа!")
    public String getDescription() {
        return description;
    }

    public LessonAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }


    public MultipartFile getImg() {
        return img;
    }

    public LessonAddBindingModel setImg(MultipartFile img) {
        this.img = img;
        return this;
    }

    //Validate video url in service, may be empty
    public String getVideoUrl() {
        return videoUrl;
    }

    public LessonAddBindingModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    @NotNull
    public Long getCourseId() {
        return courseId;
    }

    public LessonAddBindingModel setCourseId(Long courseId) {
        this.courseId = courseId;
        return this;
    }
}
