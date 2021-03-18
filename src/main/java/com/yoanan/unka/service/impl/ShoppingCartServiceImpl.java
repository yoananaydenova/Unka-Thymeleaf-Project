package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.entity.ShoppingCartEntity;
import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.view.CourseViewModel;
import com.yoanan.unka.repository.CourseRepository;
import com.yoanan.unka.repository.ShoppingCartRepository;
import com.yoanan.unka.repository.UserRepository;
import com.yoanan.unka.service.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void addCourseInCart(String username, Long courseId) {


        Optional<ShoppingCartEntity> shoppingCart = shoppingCartRepository.findByStudent_Username(username);

        CourseEntity courseEntity = courseRepository.findById(courseId).
                orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " not found!"));

        if (shoppingCart.isEmpty()) {
            UserEntity userEntity = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalStateException("User with username " + username + " not found!"));


            ShoppingCartEntity newShoppingCart = new ShoppingCartEntity(userEntity, BigDecimal.ZERO, Set.of(courseEntity));

            shoppingCartRepository.save(newShoppingCart);
        }else {
            ShoppingCartEntity shoppingCartEntity = shoppingCart.get().addCourseInCart(courseEntity);
            shoppingCartRepository.save(shoppingCartEntity);
        }
    }

    @Override
    public List<CourseViewModel> listCoursesInCart(String username) {

        Optional<ShoppingCartEntity> shoppingCart = shoppingCartRepository.findByStudent_Username(username);

        if (shoppingCart.isEmpty()) {

            UserEntity userEntity = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalStateException("User with username " + username + " not found!"));
            ShoppingCartEntity newShoppingCart = new ShoppingCartEntity(userEntity, BigDecimal.ZERO);

           shoppingCartRepository.save(newShoppingCart);
        }

        Set<CourseEntity> coursesInCart = shoppingCart.get().getCoursesInCart();

        return coursesInCart.stream()
                .map(course -> {
                    CourseViewModel courseView = modelMapper.map(course, CourseViewModel.class);
                    courseView.setTeacher(course.getTeacher().getFullName());
                    return courseView;
                }).collect(Collectors.toList());

    }
}
