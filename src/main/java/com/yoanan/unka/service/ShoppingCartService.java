package com.yoanan.unka.service;

import com.yoanan.unka.model.view.CourseViewModel;

import java.util.List;

public interface ShoppingCartService {

    void addCourseInCart(String username, Long courseId);

    List<CourseViewModel> listCoursesInCart(String username);
}
