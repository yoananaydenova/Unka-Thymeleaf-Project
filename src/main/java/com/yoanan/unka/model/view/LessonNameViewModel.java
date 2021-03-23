package com.yoanan.unka.model.view;

public class LessonNameViewModel extends BaseViewModel {
    private String title;

    public LessonNameViewModel() {
    }

    public String getTitle() {
        return title;
    }

    public LessonNameViewModel setTitle(String title) {
        this.title = title;
        return this;
    }
}
