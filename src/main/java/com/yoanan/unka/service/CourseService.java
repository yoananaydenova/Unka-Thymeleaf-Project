package com.yoanan.unka.service;

import com.yoanan.unka.model.service.CourseAddServiceModel;
import com.yoanan.unka.model.service.CourseServiceModel;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface CourseService {

    void addCourse(String username, CourseAddServiceModel courseServiceModel) throws IOException;

    boolean courseWithNameAndTeacher(String courseName, String username);

    Page<CourseServiceModel> findAllPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<CourseServiceModel> findAllByTeacherPaginated(String username, int pageNo, int pageSize, String sortField, String sortDir);

    Page<CourseServiceModel> findByCategoryPaginated(Long categoryId, int pageNo, int pageSize, String sortField, String sortDir);

    Page<CourseServiceModel> findAllByTeacherAndCategoryPaginated(String username, Long categoryId, int pageNo, int pageSize, String sortField, String sortDir);

    CourseServiceModel findById(Long id);

}
