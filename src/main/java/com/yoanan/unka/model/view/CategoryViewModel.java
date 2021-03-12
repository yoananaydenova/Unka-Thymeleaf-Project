package com.yoanan.unka.model.view;

public class CategoryViewModel extends BaseViewModel {

    private String name;
    private String description;

    public CategoryViewModel() {
    }

    public String getName() {
        return name;
    }

    public CategoryViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryViewModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
