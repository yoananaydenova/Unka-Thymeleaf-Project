package com.yoanan.unka.service;


import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.model.service.CourseServiceModel;
import com.yoanan.unka.model.service.UserRegisterServiceModel;
import com.yoanan.unka.model.service.UserServiceModel;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserService {

    void seedUsers();

    void registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel);

    boolean usernameExists(String username);

    void addRole(String username, UserRole role);

    UserEntity findByUsername(String username);

    List<UserServiceModel> findAll();

    boolean currentUserIsTeacher();

    boolean currentUserIsAdmin();

     UserServiceModel findUserById(Long userId);

    boolean findUserByIdIsLogedUser(Long id);

    void saveChangeFullName(UserServiceModel userServiceModel);

    void changeRoleOfUser(Long userId, Long newRoleId);

    String findFullName();
}
