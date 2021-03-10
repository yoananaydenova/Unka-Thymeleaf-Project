package com.yoanan.unka.service;

import com.yoanan.unka.model.service.CourseAddServiceModel;
import com.yoanan.unka.model.service.CourseServiceModel;

import java.io.IOException;
import java.util.List;

public interface CourseService {

    void addCourse(String username, CourseAddServiceModel courseServiceModel) throws IOException;

    List<CourseServiceModel> findAll();
}
