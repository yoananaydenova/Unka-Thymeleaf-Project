package com.yoanan.unka.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "lessons")
public class LessonEntity extends BaseEntity {

    private CourseEntity course;
    private String title;
    private String description;
    private String imgUrl;
    private String videoUrl;
//    private Set<ExerciseEntity> exercises = new HashSet<>();

    public LessonEntity() {
    }

    @ManyToOne(optional = false)
    public CourseEntity getCourse() {
        return course;
    }

    public LessonEntity setCourse(CourseEntity course) {
        this.course = course;
        return this;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public LessonEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    @Column(name = "description", columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public LessonEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column(name = "img")
    public String getImgUrl() {
        return imgUrl;
    }

    public LessonEntity setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    @Column(name = "video")
    public String getVideoUrl() {
        return videoUrl;
    }

    public LessonEntity setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

//    @OneToMany
//    public Set<ExerciseEntity> getExercises() {
//        return exercises;
//    }
//
//    public LessonEntity setExercises(Set<ExerciseEntity> exersise) {
//        this.exercises = exersise;
//        return this;
//    }
}
