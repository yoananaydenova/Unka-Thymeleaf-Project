package com.yoanan.unka.service;

import com.yoanan.unka.model.service.LessonAddServiceModel;
import com.yoanan.unka.model.service.LessonServiceModel;

import java.io.IOException;
import java.util.List;

public interface LessonService {
    boolean existLessonByTitleAndCourseId(String title, Long courseId);

    LessonServiceModel addLesson(LessonAddServiceModel lessonAddServiceModel) throws IOException;

    List<LessonServiceModel> findAllLessonsByCourseId(Long id);

    LessonServiceModel findLessonById(Long id);

}
