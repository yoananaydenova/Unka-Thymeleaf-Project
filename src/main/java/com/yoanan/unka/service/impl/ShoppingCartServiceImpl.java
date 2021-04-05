package com.yoanan.unka.service.impl;

import com.yoanan.unka.config.IAuthenticationFacade;
import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.entity.ShoppingCartEntity;
import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.service.CourseServiceModel;
import com.yoanan.unka.model.service.ShoppingCartServiceModel;
import com.yoanan.unka.repository.CourseRepository;
import com.yoanan.unka.repository.ShoppingCartRepository;
import com.yoanan.unka.service.ShoppingCartService;
import com.yoanan.unka.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final IAuthenticationFacade authenticationFacade;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserService userService, CourseRepository courseRepository, ModelMapper modelMapper, IAuthenticationFacade authenticationFacade) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userService = userService;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
        this.authenticationFacade = authenticationFacade;
    }


    @Override
    public void addCourseInCart(Long courseId) {

        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        Optional<ShoppingCartEntity> shoppingCart = shoppingCartRepository.findByStudent_Username(username);

        CourseEntity courseEntity = courseRepository.findById(courseId).
                orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " not found!"));

        ShoppingCartEntity newShoppingCart;
        if (shoppingCart.isEmpty()) {
            UserEntity userEntity = userService.findByUsername(username);

            newShoppingCart = new ShoppingCartEntity(userEntity, courseEntity.getPrice(), Set.of(courseEntity));
        } else {
            newShoppingCart = shoppingCart.get().addCourseInCart(courseEntity);
        }
        shoppingCartRepository.save(newShoppingCart);
    }

    @Override
    public ShoppingCartServiceModel getShoppingCart() {
        System.out.println();
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        Optional<ShoppingCartEntity> shoppingCartOpt = shoppingCartRepository.findByStudent_Username(username);

        ShoppingCartServiceModel shoppingCartService;
        if (shoppingCartOpt.isEmpty()) {

            UserEntity userEntity = userService.findByUsername(username);

            ShoppingCartEntity newShoppingCart = new ShoppingCartEntity(userEntity, BigDecimal.ZERO);

            ShoppingCartEntity savedShoppingCart = shoppingCartRepository.save(newShoppingCart);
            shoppingCartService = modelMapper.map(savedShoppingCart, ShoppingCartServiceModel.class);
        } else {

            Set<CourseServiceModel> courseServiceModels = shoppingCartOpt.get()
                    .getCoursesInCart()
                    .stream().map(course -> {
                        CourseServiceModel courseModel = modelMapper.map(course, CourseServiceModel.class);
                        courseModel.setTeacher(course.getTeacher().getFullName());
                        return courseModel;
                    }).collect(Collectors.toSet());

            shoppingCartService = modelMapper.map(shoppingCartOpt.get(), ShoppingCartServiceModel.class);
            shoppingCartService.setCoursesInCart(courseServiceModels);
        }

        return shoppingCartService;
    }


    @Override
    public void emptyShoppingCart() {

        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findByStudent_Username(username)
                .orElseThrow(() -> new IllegalStateException("Shopping cart with user with username " + username + " not found!"));


        shoppingCartEntity.setTotalPrice(BigDecimal.ZERO);
        shoppingCartEntity.getCoursesInCart().clear();
        shoppingCartRepository.save(shoppingCartEntity);
    }

    @Override
    public void payTeachersCourseWhenBuy() {

        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findByStudent_Username(username)
                .orElseThrow(() -> new IllegalStateException("Shopping cart with user with username " + username + " not found!"));


        // Pay price of course to teacher
        Set<CourseEntity> coursesInCart = shoppingCartEntity.getCoursesInCart();
        coursesInCart.forEach(course -> {
            UserEntity teacher = userService.findByUsername(course.getTeacher().getUsername());
            teacher.addIncome(course.getPrice());

        });
    }

    @Override
    public void deleteCourseFromCart(Long courseId) {
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findByStudent_Username(username)
                .orElseThrow(() -> new IllegalStateException("Shopping cart with user with username " + username + " not found!"));

        CourseEntity courseEntity = shoppingCartEntity
                .getCoursesInCart()
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " not found!"));


        shoppingCartEntity.getCoursesInCart().remove(courseEntity);
        shoppingCartEntity.setTotalPrice(shoppingCartEntity.getTotalPrice().subtract(courseEntity.getPrice()));

        shoppingCartRepository.save(shoppingCartEntity);
    }


}
