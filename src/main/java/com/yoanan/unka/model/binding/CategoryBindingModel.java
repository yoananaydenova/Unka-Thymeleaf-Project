package com.yoanan.unka.model.binding;

public class CategoryBindingModel  {

    private String name;
    private String description;


    public CategoryBindingModel() {
    }

    public String getName() {
        return name;
    }

    public CategoryBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
