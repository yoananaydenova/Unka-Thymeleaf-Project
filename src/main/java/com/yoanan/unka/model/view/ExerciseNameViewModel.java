package com.yoanan.unka.model.view;

public class ExerciseNameViewModel extends BaseViewModel {

    private String title;

    public ExerciseNameViewModel() {
    }

    public String getTitle() {
        return title;
    }

    public ExerciseNameViewModel setTitle(String title) {
        this.title = title;
        return this;
    }
}
