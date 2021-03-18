package com.yoanan.unka.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
public class UserEntity extends BaseEntity{

    private String username;
    private String fullName;
    private String password;

    private List<UserRoleEntity> roles = new ArrayList<>();
    private Set<CourseEntity> enrolledCourses = new HashSet<>();
    private Set<CourseEntity> teachCourses = new HashSet<>();
    private Set<CourseEntity> coursesInCart = new HashSet<>();

    public UserEntity() {
    }

    @Column(nullable = false)
    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String name) {
        this.username = name;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public UserEntity addRole(UserRoleEntity userRoleEntity){
        this.roles.add(userRoleEntity);
        return this;
    }

    @ManyToMany
    public Set<CourseEntity> getEnrolledCourses() {
        return enrolledCourses;
    }

    public UserEntity setEnrolledCourses(Set<CourseEntity> courses) {
        this.enrolledCourses = courses;
        return this;
    }

    @OneToMany(mappedBy="teacher")
    public Set<CourseEntity> getTeachCourses() {
        return teachCourses;
    }

    public UserEntity setTeachCourses(Set<CourseEntity> teachCourses) {
        this.teachCourses = teachCourses;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<CourseEntity> getCoursesInCart() {
        return coursesInCart;
    }

    public UserEntity setCoursesInCart(Set<CourseEntity> coursesInCart) {
        this.coursesInCart = coursesInCart;
        return this;
    }

    public UserEntity addCourseInCart(CourseEntity courseEntity){
        this.coursesInCart.add(courseEntity);
        return this;
    }

}
