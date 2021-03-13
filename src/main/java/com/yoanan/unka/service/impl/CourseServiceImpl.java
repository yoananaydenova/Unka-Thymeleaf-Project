package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.CategoryEntity;
import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.service.CourseAddServiceModel;
import com.yoanan.unka.model.service.CourseServiceModel;
import com.yoanan.unka.repository.CategoryRepository;
import com.yoanan.unka.repository.CourseRepository;
import com.yoanan.unka.service.CloudinaryService;
import com.yoanan.unka.service.CourseService;
import com.yoanan.unka.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public CourseServiceImpl(CourseRepository courseRepository, CloudinaryService cloudinaryService, UserService userService, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void addCourse(String username, CourseAddServiceModel courseAddServiceModel) throws IOException {

        CourseEntity newCourse = modelMapper.map(courseAddServiceModel, CourseEntity.class);

        MultipartFile img = courseAddServiceModel.getImg();
        String imageUrl = cloudinaryService.uploadImage(img);
        newCourse.setImgUrl(imageUrl);

        UserEntity userEntity = userService.findByUsername(username);
        newCourse.setTeacher(userEntity);

        if (courseAddServiceModel.getCategories().size() == 0) {

            CategoryEntity categoryEntity = categoryRepository
                    .findById(1L)
                    .orElseThrow(() ->
                            new IllegalStateException("Category not found! Please seed the category!"));

            newCourse.addCategory(categoryEntity);

        } else {
            Set<CategoryEntity> categoryList = courseAddServiceModel
                    .getCategories()
                    .stream()
                    .map(category -> {
                        return categoryRepository
                                .findById(Long.parseLong(category))
                                .orElseThrow(() ->
                                        new IllegalStateException("Category not found! Please seed the category!"));


                    }).collect(Collectors.toSet());

            newCourse.setCategories(categoryList);
        }

        courseRepository.saveAndFlush(newCourse);
    }

    @Override
    public List<CourseServiceModel> findAll() {
        List<CourseServiceModel> collect = courseRepository
                .findAll()
                .stream()
                .map(ce -> {
                    CourseServiceModel courseServiceModel = modelMapper.map(ce, CourseServiceModel.class);
                    courseServiceModel.setTeacher(ce.getTeacher().getFullName());
                    return courseServiceModel;
                })
                .collect(Collectors.toList());

        return collect;
    }

    @Override
    public boolean courseWithNameAndTeacher(String courseName, String username) {

        UserEntity byUsername = userService.findByUsername(username);

        boolean byNameAndTeacher = courseRepository
                .existsByNameAndTeacher(courseName, byUsername);
        System.out.println();
        return byNameAndTeacher;
    }
}
