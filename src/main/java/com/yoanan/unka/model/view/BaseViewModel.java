package com.yoanan.unka.model.view;

public abstract class BaseViewModel {

    private Long id;

    public BaseViewModel() {
    }

    public Long getId() {
        return id;
    }

    public BaseViewModel setId(Long id) {
        this.id = id;
        return this;
    }
}