package com.yoanan.unka.service;

import com.yoanan.unka.model.service.CourseAddServiceModel;
import com.yoanan.unka.model.service.CourseServiceModel;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface CourseService {

    void addCourse(String username, CourseAddServiceModel courseServiceModel) throws IOException;

    List<CourseServiceModel> findAll();

    boolean courseWithNameAndTeacher(String courseName, String username);

//    List<CourseServiceModel> findByTeacherUsername(String username);

    Page<CourseServiceModel> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<CourseServiceModel> findByTeacherPaginated(String username, int pageNo, int pageSize, String sortField, String sortDir);
}
