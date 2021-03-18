package com.yoanan.unka.service;


import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.model.service.UserRegisterServiceModel;
import com.yoanan.unka.model.view.CourseViewModel;

import java.util.List;
import java.util.Set;

public interface UserService {

    void seedUsers();

    void registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel);

    boolean usernameExists(String username);

    void addRole(String username, UserRole role);

    UserEntity findByUsername(String username);

    void addCourseInCart(String username, Long courseId);

    List<CourseViewModel> listCoursesInCart(String username);
}
