package com.yoanan.unka.web;

import com.yoanan.unka.model.binding.CourseAddBindingModel;
import com.yoanan.unka.model.service.CourseAddServiceModel;
import com.yoanan.unka.model.view.CourseViewModel;
import com.yoanan.unka.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final ModelMapper modelMapper;
    private final CourseService courseService;

    public CourseController(ModelMapper modelMapper, CourseService courseService) {
        this.modelMapper = modelMapper;
        this.courseService = courseService;
    }


    @GetMapping("/add")
    public String add() {
        return "add-course";
    }

    @PostMapping("/add")
    public String addConfirm(Model model, @ModelAttribute("courseAddBindingModel") CourseAddBindingModel courseAddBindingModel,
                             Principal principal) throws IOException {
        // TODO Valid
        CourseAddServiceModel courseAddServiceModel = modelMapper.map(courseAddBindingModel, CourseAddServiceModel.class);
        courseService.addCourse(principal.getName(), courseAddServiceModel);

        List<CourseViewModel> coursesViewModels = courseService
                .findAll()
                .stream()
                .map(cs -> modelMapper.map(cs, CourseViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute("courses", coursesViewModels);
        return "add-course";
    }
}
