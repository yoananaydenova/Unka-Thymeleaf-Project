package com.yoanan.unka.model.binding;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ChartBindingModel {

    @Expose
    private int number;
    @Expose
    private String name;
    @Expose
    private int group;
    @Expose
    private int section;

    public ChartBindingModel(int number, String name, int group, int section) {
        this.number = number;
        this.name = name;
        this.group = group;
        this.section = section;
    }

    @NotNull
    @Min(value = 100, message = "Chart number must be minimum 100!")
    @Max(value = 999, message = "Chart number must be mmaximum 999!")
    public int getNumber() {
        return number;
    }

    public ChartBindingModel setNumber(int number) {
        this.number = number;
        return this;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public ChartBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @NotNull
    @Min(value = 10, message = "Group number must be minimum 10!")
    @Max(value = 99, message = "Group number must be mmaximum 99!")
    public int getGroup() {
        return group;
    }

    public ChartBindingModel setGroup(int group) {
        this.group = group;
        return this;
    }

    @NotNull
    @Min(value = 1, message = "Section number must be minimum 1!")
    @Max(value = 9, message = "Section number must be maximum 9!")
    public int getSection() {
        return section;
    }

    public ChartBindingModel setSection(int section) {
        this.section = section;
        return this;
    }
}
