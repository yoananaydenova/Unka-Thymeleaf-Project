package com.yoanan.unka.model.view;

public class ChartViewRestModel {

    // This must be named 'name' - combination of number and name, for jsuites dropdown library
    // value - id if chart
    private Long value;
    private String group;
    private String name;

    public ChartViewRestModel() {
    }

    public Long getValue() {
        return value;
    }

    public ChartViewRestModel setValue(Long value) {
        this.value = value;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public ChartViewRestModel setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getName() {
        return name;
    }

    public ChartViewRestModel setName(String name) {
        this.name = name;
        return this;
    }
}
