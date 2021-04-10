package com.yoanan.unka.service.impl;

import com.yoanan.unka.config.IAuthenticationFacade;
import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.service.CourseServiceModel;
import com.yoanan.unka.model.service.UserRoleServiceModel;
import com.yoanan.unka.repository.CategoryRepository;
import com.yoanan.unka.repository.CourseRepository;
import com.yoanan.unka.repository.ShoppingCartRepository;
import com.yoanan.unka.service.CloudinaryService;
import com.yoanan.unka.service.CourseService;
import com.yoanan.unka.service.EnrolledCoursesService;
import com.yoanan.unka.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    CourseService courseServiceToTest;

    @Mock
    CourseRepository courseRepository;
    @Mock
    CloudinaryService cloudinaryService;
    @Mock
    EnrolledCoursesService enrolledCoursesService;
    @Mock
    UserService userService;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    ShoppingCartRepository shoppingCartRepository;
    @Mock
    IAuthenticationFacade authenticationFacade;

    ModelMapper modelMapper;


    @BeforeEach
    public void SetUp() {
        modelMapper = new ModelMapper();
        courseServiceToTest = new CourseServiceImpl(
                courseRepository,
                cloudinaryService,
                enrolledCoursesService,
                userService,
                modelMapper,
                categoryRepository,
                shoppingCartRepository,
                authenticationFacade
        );

    }

    @Test
    public void findThreeCoursesWithLowerPrice_whenCanNotFindCourseInDB_shouldReturnEmptyList(){

        when(courseRepository.findFirst3ByOrderByPriceAsc())
                .thenReturn(new ArrayList<>());

        List<CourseServiceModel> threeCoursesWithLowerPrice = courseServiceToTest.findThreeCoursesWithLowerPrice();

        Assertions.assertEquals(0,threeCoursesWithLowerPrice.size());

    }


    @Test
    public void findThreeCoursesWithLowerPrice_whenFindCourseInDB_shouldReturnCorrect(){

        CourseEntity firstCourse = new CourseEntity();
        firstCourse.setName("First Course");
        firstCourse.setPrice(BigDecimal.valueOf(1));

        CourseEntity secondCourse = new CourseEntity();
        secondCourse.setName("Second Course");
        secondCourse.setPrice(BigDecimal.valueOf(2));

        CourseEntity zeroCourse = new CourseEntity();
        zeroCourse.setName("Zero Course");
        zeroCourse.setPrice(BigDecimal.ZERO);

        List<CourseEntity> courseSortedList = List.of(firstCourse, secondCourse, zeroCourse)
                .stream()
                .sorted((f, s) -> f.getPrice().compareTo(s.getPrice()))
                .collect(Collectors.toList());


        when(courseRepository.findFirst3ByOrderByPriceAsc())
                .thenReturn(courseSortedList);

        List<CourseServiceModel> threeCoursesWithLowerPrice = courseServiceToTest.findThreeCoursesWithLowerPrice();

        Assertions.assertEquals(3,threeCoursesWithLowerPrice.size());
        Assertions.assertEquals(courseSortedList.get(0).getPrice(),threeCoursesWithLowerPrice.get(0).getPrice());
        Assertions.assertEquals(courseSortedList.get(1).getPrice(),threeCoursesWithLowerPrice.get(1).getPrice());
        Assertions.assertEquals(courseSortedList.get(2).getPrice(),threeCoursesWithLowerPrice.get(2).getPrice());

        Assertions.assertEquals(courseSortedList.get(0).getName(),threeCoursesWithLowerPrice.get(0).getName());
        Assertions.assertEquals(courseSortedList.get(1).getName(),threeCoursesWithLowerPrice.get(1).getName());
        Assertions.assertEquals(courseSortedList.get(2).getName(),threeCoursesWithLowerPrice.get(2).getName());
    }
}
