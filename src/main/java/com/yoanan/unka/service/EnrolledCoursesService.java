package com.yoanan.unka.service;

import com.yoanan.unka.model.service.CourseServiceModel;

import java.util.List;

public interface EnrolledCoursesService {

    void buyCourses();

    boolean isEnrolledByUsernameAndCourseId(Long courseId);

    List<CourseServiceModel> findAllMyEnrolledCourses();
    
}
