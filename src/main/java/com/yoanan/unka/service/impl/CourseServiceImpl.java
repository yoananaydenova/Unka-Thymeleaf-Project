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
import org.springframework.data.domain.*;
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

        return courseRepository
                .findAll()
                .stream()
                .map(ce -> {
                    CourseServiceModel courseServiceModel = modelMapper.map(ce, CourseServiceModel.class);
                    courseServiceModel.setTeacher(ce.getTeacher().getFullName());
                    return courseServiceModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean courseWithNameAndTeacher(String courseName, String username) {

        UserEntity byUsername = userService.findByUsername(username);

        return courseRepository
                .existsByNameAndTeacher_Username(courseName, username);
    }

//    @Override
////    public List<CourseServiceModel> findByTeacherUsername(String username) {
////
////        return courseRepository
////                .findAllByTeacher_Username(username)
////                .stream()
////                .map(ce -> {
////                    CourseServiceModel courseServiceModel = modelMapper.map(ce, CourseServiceModel.class);
////                    courseServiceModel.setTeacher(ce.getTeacher().getFullName());
////                    return courseServiceModel;
////                })
////                .collect(Collectors.toList());
////
////    }

    @Override
    public Page<CourseServiceModel> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<CourseEntity> coursesPage = courseRepository
                .findAll(pageable);

        int totalElements = (int) coursesPage.getTotalElements();

        return new PageImpl<CourseServiceModel>(coursesPage
                .stream()
                .map(course ->{
                    CourseServiceModel courseModel = modelMapper.map(course, CourseServiceModel.class);
                    courseModel.setTeacher(course.getTeacher().getFullName());
                    return courseModel;
                })
                .collect(Collectors.toList()), pageable, totalElements);

    }

    @Override
    public Page<CourseServiceModel> findByTeacherPaginated(String username,
                                                           int pageNo, int pageSize, String sortField, String sortDirection) {


        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<CourseEntity> coursesPage = courseRepository
                .findAllByTeacher_Username(username, pageable);

        int totalElements = (int) coursesPage.getTotalElements();

        return new PageImpl<CourseServiceModel>(coursesPage
                .stream()
                .map(course ->{
                    CourseServiceModel courseModel = modelMapper.map(course, CourseServiceModel.class);
                    courseModel.setTeacher(course.getTeacher().getFullName());
                    return courseModel;
                })
                .collect(Collectors.toList()), pageable, totalElements);
    }


}
