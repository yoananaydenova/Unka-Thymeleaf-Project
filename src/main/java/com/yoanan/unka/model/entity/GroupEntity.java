package com.yoanan.unka.model.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "groups_table")
public class GroupEntity extends BaseEntity{

    @Expose
    private int number;
    @Expose
    private String name;

    public GroupEntity() {
    }

    @Column(name="number", nullable = false, unique = true)
    public int getNumber() {
        return number;
    }

    public GroupEntity setNumber(int number) {
        this.number = number;
        return this;
    }

    @Column(name="name", nullable = false)
    public String getName() {
        return name;
    }

    public GroupEntity setName(String name) {
        this.name = name;
        return this;
    }

}
