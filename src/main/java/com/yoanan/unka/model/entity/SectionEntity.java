package com.yoanan.unka.model.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sections")
public class SectionEntity extends BaseEntity {

    @Expose
    private int number;
    @Expose
    private String name;

    public SectionEntity() {
    }

    @Column(name="number", nullable = false, unique = true)
    public int getNumber() {
        return number;
    }

    public SectionEntity setNumber(int number) {
        this.number = number;
        return this;
    }

    @Column(name="name", nullable = false)
    public String getName() {
        return name;
    }

    public SectionEntity setName(String name) {
        this.name = name;
        return this;
    }
}
