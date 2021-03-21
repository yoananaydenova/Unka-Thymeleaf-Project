package com.yoanan.unka.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "enrolled_courses")
public class EnrolledCoursesEntity extends BaseEntity {

    private UserEntity student;
    //TODO update when delete course
    private Set<CourseEntity> myEnrolledCourses = new HashSet<>();

    public EnrolledCoursesEntity() {
    }

    public EnrolledCoursesEntity(UserEntity student, Set<CourseEntity> enrolledCourses) {
        this.student = student;
        this.myEnrolledCourses = enrolledCourses;
    }

    @OneToOne
    public UserEntity getStudent() {
        return student;
    }

    public EnrolledCoursesEntity setStudent(UserEntity student) {
        this.student = student;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<CourseEntity> getMyEnrolledCourses() {
        return myEnrolledCourses;
    }

    public EnrolledCoursesEntity setMyEnrolledCourses(Set<CourseEntity> myEnrolledCourses) {
        this.myEnrolledCourses = myEnrolledCourses;
        return this;
    }

    public EnrolledCoursesEntity addCoursesToEnrolled(Set<CourseEntity> courses) {
        this.myEnrolledCourses.addAll(courses);
        return this;
    }
}
