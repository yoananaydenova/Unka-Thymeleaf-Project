package com.yoanan.unka.model.view;

public class LessonViewModel extends BaseViewModel {

    private CourseViewModel course;
    private String title;
    private String description;
    private String imgUrl;
    private String videoUrl;

    public LessonViewModel() {
    }

    public CourseViewModel getCourse() {
        return course;
    }

    public LessonViewModel setCourse(CourseViewModel course) {
        this.course = course;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public LessonViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LessonViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public LessonViewModel setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public LessonViewModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }
}
