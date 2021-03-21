package com.yoanan.unka.model.service;

import com.yoanan.unka.model.view.CourseViewModel;

import java.math.BigDecimal;
import java.util.Set;

public class ShoppingCartServiceModel extends BaseServiceModel{


    private BigDecimal totalPrice;
    private Set<CourseServiceModel> coursesInCart;

    public ShoppingCartServiceModel() {
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public ShoppingCartServiceModel setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public Set<CourseServiceModel> getCoursesInCart() {
        return coursesInCart;
    }

    public ShoppingCartServiceModel setCoursesInCart(Set<CourseServiceModel> coursesInCart) {
        this.coursesInCart = coursesInCart;
        return this;
    }
}
