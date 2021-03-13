package com.yoanan.unka.repository;

import com.yoanan.unka.model.entity.CourseEntity;
import com.yoanan.unka.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

//     findByNameAndTeacher(String courseName, UserEntity teacher);

    boolean existsByNameAndTeacher(String courseName, UserEntity teacher);
}
