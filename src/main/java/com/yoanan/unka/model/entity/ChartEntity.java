package com.yoanan.unka.model.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "charts")
public class ChartEntity extends BaseEntity {

    private int number;
    private String name;
    private GroupEntity group;
    private SectionEntity section;

    public ChartEntity() {
    }

    @Column(name="number", nullable = false, unique = true)
    public int getNumber() {
        return number;
    }

    public ChartEntity setNumber(int number) {
        this.number = number;
        return this;
    }

    @Column(name="name", nullable = false)
    public String getName() {
        return name;
    }

    public ChartEntity setName(String name) {
        this.name = name;
        return this;
    }

    @ManyToOne
    public GroupEntity getGroup() {
        return group;
    }

    public ChartEntity setGroup(GroupEntity group) {
        this.group = group;
        return this;
    }

    @ManyToOne
    public SectionEntity getSection() {
        return section;
    }

    public ChartEntity setSection(SectionEntity section) {
        this.section = section;
        return this;
    }
}
