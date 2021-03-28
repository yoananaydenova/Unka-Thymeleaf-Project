package com.yoanan.unka.model.service;

public class ChartServiceModel extends BaseServiceModel {

    private int number;
    private String name;
    private String groupName;

    public ChartServiceModel() {
    }

    public int getNumber() {
        return number;
    }

    public ChartServiceModel setNumber(int number) {
        this.number = number;
        return this;
    }

    public String getName() {
        return name;
    }

    public ChartServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public ChartServiceModel setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }
}
