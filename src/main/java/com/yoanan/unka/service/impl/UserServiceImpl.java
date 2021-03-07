package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.entity.UserRoleEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.model.service.UserRegisterServiceModel;
import com.yoanan.unka.repository.UserRepository;
import com.yoanan.unka.repository.UserRoleRepository;
import com.yoanan.unka.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void seedUsers() {

        if (userRepository.count() == 0) {
            UserRoleEntity studentRole = userRoleRepository.save(new UserRoleEntity().setRole(UserRole.STUDENT));
            UserRoleEntity teacherRole = userRoleRepository.save(new UserRoleEntity().setRole(UserRole.TEACHER));
            UserRoleEntity adminRole = userRoleRepository.save(new UserRoleEntity().setRole(UserRole.ADMIN));

            // Student
            UserEntity student = new UserEntity()
                    .setName("student")
                    .setPassword(passwordEncoder.encode("123"))
                    .setRoles(List.of(studentRole));

            UserEntity teacher = new UserEntity()
                    .setName("teacher")
                    .setPassword(passwordEncoder.encode("123"));
            // Teacher has 2 roles
            teacher.setRoles(List.of(teacherRole, studentRole));

            UserEntity admin = new UserEntity()
                    .setName("admin")
                    .setPassword(passwordEncoder.encode("123"));

            // Admin has 3 roles
            admin.setRoles(List.of(adminRole, teacherRole, studentRole));

            userRepository.saveAll(List.of(student, teacher, admin));
        }
    }

    @Override
    public void registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel) {
        // TODO
        throw new UnsupportedOperationException("NOT YET!");
    }


}
