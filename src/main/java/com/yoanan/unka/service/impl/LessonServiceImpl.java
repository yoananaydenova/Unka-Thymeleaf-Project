package com.yoanan.unka.service.impl;

import com.yoanan.unka.config.IAuthenticationFacade;
import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.entity.LessonEntity;
import com.yoanan.unka.model.service.CourseServiceModel;
import com.yoanan.unka.model.service.LessonAddServiceModel;
import com.yoanan.unka.model.service.LessonServiceModel;
import com.yoanan.unka.repository.LessonRepository;
import com.yoanan.unka.service.CloudinaryService;
import com.yoanan.unka.service.CourseService;
import com.yoanan.unka.service.LessonService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CloudinaryService cloudinaryService;
    private final CourseService courseService;
    private final IAuthenticationFacade authenticationFacade;

    private final ModelMapper modelMapper;

    public LessonServiceImpl(LessonRepository lessonRepository, CloudinaryService cloudinaryService, CourseService courseService, IAuthenticationFacade authenticationFacade, ModelMapper modelMapper) {
        this.lessonRepository = lessonRepository;
        this.cloudinaryService = cloudinaryService;
        this.courseService = courseService;
        this.authenticationFacade = authenticationFacade;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean existLessonByTitleAndCourseId(String title, Long courseId) {
        return lessonRepository
                .existsByTitleAndCourse_Id(title, courseId);
    }

    @Override
    public LessonServiceModel addLesson(LessonAddServiceModel lessonAddServiceModel) throws IOException {

        Authentication authentication = authenticationFacade.getAuthentication();
        String username = authentication.getName();
        CourseServiceModel courseServiceModel = courseService.findCourseById(lessonAddServiceModel.getCourseId());
        CourseEntity courseEntity = modelMapper.map(courseServiceModel,CourseEntity.class );


        if (!courseEntity.getTeacher().getUsername().equals(username)) {
            throw new IllegalArgumentException("User with username " + username + " is not a teacher of this course and can not create lesson!");
        }

        LessonEntity newLessonEntity = modelMapper.map(lessonAddServiceModel, LessonEntity.class);

        // Set img, if exist
        MultipartFile img = lessonAddServiceModel.getImg();
        String imageUrl;
        if (img.isEmpty()) {
            imageUrl = null;
        } else {
            imageUrl = cloudinaryService.uploadImage(img);
        }
        newLessonEntity.setImgUrl(imageUrl);

        // Set video if exist
        if (lessonAddServiceModel.getVideoUrl().isEmpty()) {
            newLessonEntity.setVideoUrl(null);
        } else {
            String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(lessonAddServiceModel.getVideoUrl());
            String videoId;
            if (matcher.find()) {
                videoId = matcher.group();
                newLessonEntity.setVideoUrl(videoId);
            }
        }
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
