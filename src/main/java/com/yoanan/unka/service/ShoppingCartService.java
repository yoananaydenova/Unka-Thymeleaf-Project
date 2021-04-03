package com.yoanan.unka.service;

import com.yoanan.unka.model.service.ShoppingCartServiceModel;

public interface ShoppingCartService {

    void addCourseInCart(Long courseId);

    ShoppingCartServiceModel getShoppingCart();

    void emptyShoppingCart();

    void payTeachersCourseWhenBuy();

    void deleteCourseFromCart(Long courseId);
}
