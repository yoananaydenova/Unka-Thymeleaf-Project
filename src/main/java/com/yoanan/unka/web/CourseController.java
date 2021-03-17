package com.yoanan.unka.web;

import com.yoanan.unka.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final ModelMapper modelMapper;
    private final CourseService courseService;

    public CourseController(ModelMapper modelMapper, CourseService courseService) {
        this.modelMapper = modelMapper;
        this.courseService = courseService;
    }


    @GetMapping("/{id}")
    public String courseDetail(@PathVariable Long id){

        courseService.findById(id);

        return "details-course";
    }
}
