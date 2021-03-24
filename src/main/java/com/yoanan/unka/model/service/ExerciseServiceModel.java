package com.yoanan.unka.model.service;

public class ExerciseServiceModel extends BaseServiceModel {

    private LessonServiceModel lesson;
    private String title;
    private String description;
    private String text;


    public ExerciseServiceModel() {
    }

    public LessonServiceModel getLesson() {
        return lesson;
    }

    public ExerciseServiceModel setLesson(LessonServiceModel lesson) {
        this.lesson = lesson;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ExerciseServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ExerciseServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getText() {
        return text;
    }

    public ExerciseServiceModel setText(String text) {
        this.text = text;
        return this;
    }

}
