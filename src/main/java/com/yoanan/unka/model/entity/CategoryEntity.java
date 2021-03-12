package com.yoanan.unka.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="categories")
public class CategoryEntity extends BaseEntity{

    private String name;
    private String description;
    private Set<CourseEntity> courses = new HashSet<>();

    public CategoryEntity() {
    }

    public CategoryEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    @Column(name="name", nullable = false)
    public CategoryEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(name="description", columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public CategoryEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @ManyToMany(mappedBy = "categories", targetEntity = CourseEntity.class, fetch = FetchType.EAGER)
    public Set<CourseEntity> getCourses() {
        return courses;
    }

    public CategoryEntity setCourses(Set<CourseEntity> courses) {
        this.courses = courses;
        return this;
    }

    public CategoryEntity addCourse(CourseEntity courseEntity) {
        this.courses.add(courseEntity);
        return this;
    }
}
