package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.entity.UserRoleEntity;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.model.service.UserRegisterServiceModel;
import com.yoanan.unka.model.view.CourseViewModel;
import com.yoanan.unka.repository.CourseRepository;
import com.yoanan.unka.repository.UserRepository;
import com.yoanan.unka.repository.UserRoleRepository;
import com.yoanan.unka.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UnkaUserDetailsService unkaUserDetailsService;


    public UserServiceImpl(UserRepository userRepository, CourseRepository courseRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, UnkaUserDetailsService unkaUserDetailsService) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.unkaUserDetailsService = unkaUserDetailsService;

    }


    @Override
    public void seedUsers() {

        if (userRepository.count() == 0) {
            UserRoleEntity studentRole = userRoleRepository.save(new UserRoleEntity().setRole(UserRole.STUDENT));
            UserRoleEntity teacherRole = userRoleRepository.save(new UserRoleEntity().setRole(UserRole.TEACHER));
            UserRoleEntity adminRole = userRoleRepository.save(new UserRoleEntity().setRole(UserRole.ADMIN));

            // Student
            UserEntity student = new UserEntity()
                    .setUsername("student")
                    .setFullName("First Student")
                    .setPassword(passwordEncoder.encode("123"))
                    .setRoles(List.of(studentRole));

            UserEntity teacher = new UserEntity()
                    .setUsername("teacher")
                    .setFullName("First Teacher")
                    .setPassword(passwordEncoder.encode("123"));
            // Teacher has 2 roles
            teacher.setRoles(List.of(teacherRole, studentRole));

            UserEntity admin = new UserEntity()
                    .setUsername("admin")
                    .setFullName("Admin Adminov")
                    .setPassword(passwordEncoder.encode("123"));

            // Admin has 3 roles
            admin.setRoles(List.of(adminRole, teacherRole, studentRole));

            userRepository.saveAll(List.of(student, teacher, admin));
        }
    }

    @Override
    public void registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel) {
        UserEntity newUser = modelMapper.map(userRegisterServiceModel, UserEntity.class);

        // Save user in DB
        newUser.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));

        UserRoleEntity userRoleEntity = userRoleRepository
                .findByRole(UserRole.STUDENT)
                .orElseThrow(() -> new IllegalStateException("User role not found! Please seed the roles!"));

        newUser.addRole(userRoleEntity);

        newUser = userRepository.save(newUser);

        // Login user

        UserDetails principal = unkaUserDetailsService.loadUserByUsername(newUser.getUsername());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        principal,
                        newUser.getPassword(),
                        principal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public void addRole(String username, UserRole role) {

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User with that username not found!"));


        UserRoleEntity userRoleEntity = userRoleRepository
                .findByRole(UserRole.TEACHER)
                .orElseThrow(() -> new IllegalStateException("User role not found! Please seed the roles!"));


        UserDetails principal = unkaUserDetailsService.loadUserByUsername(username);

        //Check user don`t have role
        boolean existRole = principal.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));

        //Add role to user
        if (principal != null && !existRole) {
            userEntity.addRole(userRoleEntity);
            userRepository.save(userEntity);
        }
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalStateException("User with username " + username + " not found!"));
    }

    @Override
    public void addCourseInCart(String username, Long courseId) {

        System.out.println();
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User with username " + username + " not found!"));

        CourseEntity courseEntity = courseRepository.findById(courseId).
                orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " not found!"));
        System.out.println();
        userEntity.addCourseInCart(courseEntity);
        userRepository.save(userEntity);
    }

    @Override
    public List<CourseViewModel> listCoursesInCart(String username) {

        UserEntity userEntity = userRepository
                .findByUsername(username).orElseThrow(() -> new IllegalStateException("User with username " + username + " not found!"));

        Set<CourseEntity> coursesInCart = userEntity.getCoursesInCart();

       return coursesInCart.stream()
                .map(course -> {
                    CourseViewModel courseView = modelMapper.map(course, CourseViewModel.class);
                    courseView.setTeacher(course.getTeacher().getFullName());
                    return courseView;
                }).collect(Collectors.toList());

    }


}
