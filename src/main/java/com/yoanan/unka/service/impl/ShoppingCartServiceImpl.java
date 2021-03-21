package com.yoanan.unka.service.impl;

import com.yoanan.unka.config.IAuthenticationFacade;
import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.entity.ShoppingCartEntity;
import com.yoanan.unka.model.entity.UserEntity;
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

            newShoppingCart = new ShoppingCartEntity(userEntity, BigDecimal.ZERO, Set.of(courseEntity));
        } else {
            newShoppingCart = shoppingCart.get().addCourseInCart(courseEntity);
        }
        shoppingCartRepository.save(newShoppingCart);
    }

    @Override
    public ShoppingCartServiceModel getShoppingCart() {

        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        Optional<ShoppingCartEntity> shoppingCart = shoppingCartRepository.findByStudent_Username(username);

        ShoppingCartEntity shoppingCartServiceModel;
        if (shoppingCart.isEmpty()) {

            UserEntity userEntity = userService.findByUsername(username);

            ShoppingCartEntity newShoppingCart = new ShoppingCartEntity(userEntity, BigDecimal.ZERO);

            shoppingCartServiceModel = shoppingCartRepository.save(newShoppingCart);
        } else {
            shoppingCartServiceModel = shoppingCart.get();
        }

        return modelMapper.map(shoppingCartServiceModel, ShoppingCartServiceModel.class);
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


}
