package com.yoanan.unka.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shopping_carts")
public class ShoppingCartEntity extends BaseEntity {

    private UserEntity student;
    //TODO update when buy/delete course
    private BigDecimal totalPrice;
    private Set<CourseEntity> coursesInCart = new HashSet<>();

    public ShoppingCartEntity() {
    }

    public ShoppingCartEntity(UserEntity student, BigDecimal totalPrice, Set<CourseEntity> coursesInCart) {
        this.student = student;
        this.totalPrice = totalPrice;
        this.coursesInCart = coursesInCart;
    }

    public ShoppingCartEntity(UserEntity student, BigDecimal totalPrice) {
        this.student = student;
        this.totalPrice = totalPrice;
    }

    @OneToOne
    public UserEntity getStudent() {
        return student;
    }

    public ShoppingCartEntity setStudent(UserEntity student) {
        this.student = student;
        return this;
    }

    @Column(name = "total_price", nullable = false)
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public ShoppingCartEntity setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<CourseEntity> getCoursesInCart() {
        return coursesInCart;
    }

    public ShoppingCartEntity setCoursesInCart(Set<CourseEntity> coursesInCart) {
        this.coursesInCart = coursesInCart;
        return this;
    }

    public ShoppingCartEntity addCourseInCart(CourseEntity courseEntity) {
        this.setTotalPrice(this.getTotalPrice().add(courseEntity.getPrice()));
        this.coursesInCart.add(courseEntity);
        return this;
    }
}
