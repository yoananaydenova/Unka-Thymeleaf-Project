package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.entity.UserEntity;
import com.yoanan.unka.model.service.CourseAddServiceModel;
import com.yoanan.unka.model.service.CourseServiceModel;
import com.yoanan.unka.repository.CourseRepository;
import com.yoanan.unka.service.CloudinaryService;
import com.yoanan.unka.service.CourseService;
import com.yoanan.unka.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public CourseServiceImpl(CourseRepository courseRepository, CloudinaryService cloudinaryService, UserService userService, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addCourse(String username, CourseAddServiceModel courseAddServiceModel) throws IOException {

        CourseEntity newCourse = modelMapper.map(courseAddServiceModel, CourseEntity.class);

        System.out.println();
        MultipartFile img = courseAddServiceModel.getImg();
        String imageUrl = cloudinaryService.uploadImage(img);
        System.out.println();
        newCourse.setImgUrl(imageUrl);

        UserEntity userEntity = userService.findByUsername(username);
        newCourse.setTeacher(userEntity);

        courseRepository.save(newCourse);
    }

    @Override
    public List<CourseServiceModel> findAll() {
       return courseRepository
                .findAll()
                .stream()
                .map(ce-> modelMapper.map(ce, CourseServiceModel.class))
                .collect(Collectors.toList());
    }
}
