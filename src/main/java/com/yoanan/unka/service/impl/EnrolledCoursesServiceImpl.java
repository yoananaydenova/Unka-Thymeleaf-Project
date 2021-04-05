package com.yoanan.unka.service.impl;

import com.yoanan.unka.config.IAuthenticationFacade;
import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.entity.EnrolledCoursesEntity;
import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.service.CourseServiceModel;
import com.yoanan.unka.model.service.ShoppingCartServiceModel;
import com.yoanan.unka.repository.EnrolledCoursesRepository;
import com.yoanan.unka.service.EnrolledCoursesService;
import com.yoanan.unka.service.ShoppingCartService;
import com.yoanan.unka.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EnrolledCoursesServiceImpl implements EnrolledCoursesService {

    private final EnrolledCoursesRepository enrolledCoursesRepository;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final IAuthenticationFacade authenticationFacade;

    public EnrolledCoursesServiceImpl(EnrolledCoursesRepository enrolledCoursesRepository, ShoppingCartService shoppingCartService, UserService userService, ModelMapper modelMapper, IAuthenticationFacade authenticationFacade) {
        this.enrolledCoursesRepository = enrolledCoursesRepository;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authenticationFacade = authenticationFacade;
    }


    @Transactional
    @Override
    public void buyCourses() {

        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        ShoppingCartServiceModel shoppingCartServiceModel = shoppingCartService.getShoppingCart();

        Set<CourseServiceModel> coursesInCartServiceModel = shoppingCartServiceModel.getCoursesInCart();
        if (coursesInCartServiceModel.size() > 0) {
            Optional<EnrolledCoursesEntity> enrolledCoursesOpt = enrolledCoursesRepository.findByStudent_Username(username);
            Set<CourseEntity> coursesToSave = coursesInCartServiceModel
                    .stream()
                    .map(csm -> modelMapper.map(csm, CourseEntity.class))
                    .collect(Collectors.toSet());

            EnrolledCoursesEntity newEnrolledCoursesEntity;
            if (enrolledCoursesOpt.isEmpty()) {
                UserEntity userEntity = userService.findByUsername(username);
                newEnrolledCoursesEntity = new EnrolledCoursesEntity(userEntity, coursesToSave);
            } else {
                newEnrolledCoursesEntity = enrolledCoursesOpt.get().addCoursesToEnrolled(coursesToSave);
            }


            enrolledCoursesRepository.save(newEnrolledCoursesEntity);
            shoppingCartService.payTeachersCourseWhenBuy();
            shoppingCartService.emptyShoppingCart();
        }

    }

    @Override
    public boolean isEnrolledByUsernameAndCourseId(Long courseId) {
        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();
        return enrolledCoursesRepository.findByStudent_UsernameAndMyEnrolledCourses_Id(username, courseId).isPresent();
    }

    @Override
    public List<CourseServiceModel> findAllMyEnrolledCourses() {


        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        Optional<EnrolledCoursesEntity> enrolledCoursesEntityOpt = enrolledCoursesRepository.findByStudent_Username(username);

        if (enrolledCoursesEntityOpt.isEmpty()) {
            return new ArrayList<>();
        }
        return enrolledCoursesEntityOpt.get().getMyEnrolledCourses()
                .stream()
                .map(course ->  modelMapper.map(course, CourseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public int findAllMyEnrolledCoursesNumber() {

        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();

        Optional<EnrolledCoursesEntity> enrolledCoursesEntityOpt = enrolledCoursesRepository.findByStudent_Username(username);

        if (enrolledCoursesEntityOpt.isEmpty()) {
            return 0;
        }
        return enrolledCoursesEntityOpt.get().getMyEnrolledCourses().size();
    }

    @Override
    public int findAllEnrolledCoursesNumberByUserId(Long userId) {
        Optional<EnrolledCoursesEntity> enrolledCoursesEntityOpt = enrolledCoursesRepository.findByStudent_Id(userId);
        if (enrolledCoursesEntityOpt.isEmpty()) {
            return 0;
        }
        return enrolledCoursesEntityOpt.get().getMyEnrolledCourses().size();
    }


}
