package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.entity.LessonEntity;
import com.yoanan.unka.model.service.LessonAddServiceModel;
import com.yoanan.unka.model.service.LessonServiceModel;
import com.yoanan.unka.repository.CourseRepository;
import com.yoanan.unka.repository.LessonRepository;
import com.yoanan.unka.service.CloudinaryService;
import com.yoanan.unka.service.CourseService;
import com.yoanan.unka.service.LessonService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CloudinaryService cloudinaryService;
    private final CourseService courseService;

    private final ModelMapper modelMapper;

    public LessonServiceImpl(LessonRepository lessonRepository, CloudinaryService cloudinaryService, CourseService courseService, ModelMapper modelMapper) {
        this.lessonRepository = lessonRepository;
        this.cloudinaryService = cloudinaryService;
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean existLessonByTitleAndCourseId(String title, Long courseId) {
        return lessonRepository
                .existsByTitleAndCourse_Id(title, courseId);
    }

    @Override
    public LessonServiceModel addLesson(LessonAddServiceModel lessonAddServiceModel) throws IOException {

        LessonEntity newLessonEntity = modelMapper.map(lessonAddServiceModel, LessonEntity.class);

        MultipartFile img = lessonAddServiceModel.getImg();
        String imageUrl = cloudinaryService.uploadImage(img);
        newLessonEntity.setImgUrl(imageUrl);

        CourseEntity courseEntity = courseService.findEntityById(lessonAddServiceModel.getCourseId());
        newLessonEntity.setCourse(courseEntity);
        newLessonEntity.setId(null);

      LessonEntity savedLesson = lessonRepository.save(newLessonEntity);

        return modelMapper.map(savedLesson, LessonServiceModel.class);
    }

    @Override
    public List<LessonServiceModel> findAllLessonsByCourseId(Long id) {
        return lessonRepository
                .findAllByCourse_Id(id)
                .stream()
                .map(l -> modelMapper.map(l, LessonServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public LessonServiceModel findLessonById(Long id) {
        LessonEntity lessonEntity = lessonRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalStateException("Lesson with id " + id + " not found!"));
       return modelMapper.map(lessonEntity, LessonServiceModel.class);
    }
}
