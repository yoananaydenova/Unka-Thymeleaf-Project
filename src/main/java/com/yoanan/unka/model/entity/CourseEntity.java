package com.yoanan.unka.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="courses")
public class CourseEntity extends BaseEntity {

    private String name;
    private String imgUrl;
    private String description;
    private UserEntity teacher;
    private Set<UserEntity> students = new HashSet<>();

    public CourseEntity() {
    }

    @Column(name="name", nullable = false)
    public String getName() {
        return name;
    }

    public CourseEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(name="img", nullable = false)
    public String getImgUrl() {
        return imgUrl;
    }

    public CourseEntity setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    @Column(name="description",columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public CourseEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @ManyToOne
    public UserEntity getTeacher() {
        return teacher;
    }

    public CourseEntity setTeacher(UserEntity teacher) {
        this.teacher = teacher;
        return this;
    }

    @ManyToMany(mappedBy = "enrolledCourses", targetEntity = UserEntity.class)
    public Set<UserEntity> getStudents() {
        return students;
    }

    public CourseEntity setStudents(Set<UserEntity> users) {
        this.students = users;
        return this;
    }
}
