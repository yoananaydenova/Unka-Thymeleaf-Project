package com.yoanan.unka.web;

import com.yoanan.unka.model.service.CourseServiceModel;
import com.yoanan.unka.model.view.CourseViewModel;
import com.yoanan.unka.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

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
    public String courseDetail(@PathVariable Long id, Model model) {

        CourseServiceModel courseById = courseService.findCourseById(id);
        // Add button Adding in shopping cart
        CourseViewModel courseViewModel = modelMapper.map(courseService.findCourseById(id), CourseViewModel.class);

        // TODO new view model with details of teacher, list with lessons
        model.addAttribute("courseViewModel", courseViewModel);

        return "details-course";
    }
}
