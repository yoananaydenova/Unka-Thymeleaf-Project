package com.yoanan.unka.model.view;

import java.util.Set;

public class ShoppingCartViewModel extends BaseViewModel{

    private String totalPrice;
    private Set<CourseViewModel> coursesInCart;

    public ShoppingCartViewModel() {

    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public ShoppingCartViewModel setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public Set<CourseViewModel> getCoursesInCart() {
        return coursesInCart;
    }

    public ShoppingCartViewModel setCoursesInCart(Set<CourseViewModel> coursesInCart) {
        this.coursesInCart = coursesInCart;
        return this;
    }
}
