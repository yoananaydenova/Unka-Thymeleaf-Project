package com.yoanan.unka.model.view;

public class ExerciseViewModel extends BaseViewModel{

    private LessonViewModel lesson;
    private String title;
    private String description;
    private String text;

    public ExerciseViewModel() {
    }

    public LessonViewModel getLesson() {
        return lesson;
    }

    public ExerciseViewModel setLesson(LessonViewModel lesson) {
        this.lesson = lesson;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ExerciseViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ExerciseViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getText() {
        return text;
    }

    public ExerciseViewModel setText(String text) {
        this.text = text;
        return this;
    }
}
