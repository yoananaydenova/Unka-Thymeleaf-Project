package com.yoanan.unka.service;

import com.yoanan.unka.model.service.CourseAddServiceModel;
import com.yoanan.unka.model.service.CourseServiceModel;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface CourseService {

    void addCourse(CourseAddServiceModel courseServiceModel) throws IOException;

    boolean courseWithNameAndTeacher(String courseName);

    Page<CourseServiceModel> findAllPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<CourseServiceModel> findAllByTeacherPaginated(int pageNo, int pageSize, String sortField, String sortDir);

    Page<CourseServiceModel> findByCategoryPaginated(Long categoryId, int pageNo, int pageSize, String sortField, String sortDir);

    Page<CourseServiceModel> findAllByTeacherAndCategoryPaginated(Long categoryId, int pageNo, int pageSize, String sortField, String sortDir);

    CourseServiceModel findCourseById( Long id);


}
