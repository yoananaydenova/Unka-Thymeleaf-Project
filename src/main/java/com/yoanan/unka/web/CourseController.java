package com.yoanan.unka.web;

import com.yoanan.unka.model.binding.CourseAddBindingModel;
import com.yoanan.unka.model.service.CategoryServiceModel;
import com.yoanan.unka.model.service.CourseAddServiceModel;
import com.yoanan.unka.model.view.CategoryViewModel;
import com.yoanan.unka.model.view.CourseViewModel;
import com.yoanan.unka.service.CategoryService;
import com.yoanan.unka.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final ModelMapper modelMapper;
    private final CourseService courseService;
    private final CategoryService categoryService;

    public CourseController(ModelMapper modelMapper, CourseService courseService, CategoryService categoryService) {
        this.modelMapper = modelMapper;
        this.courseService = courseService;
        this.categoryService = categoryService;
    }


    @GetMapping("/add")
    public String add(Model model) {
        if (!model.containsAttribute("courseAddBindingModel")) {
            model.addAttribute("courseAddBindingModel", new CourseAddBindingModel());
        }


        // add category checkbox
        List<CategoryViewModel> categoryViews = categoryService
                .findAll()
                .stream()
                .map(csm -> modelMapper.map(csm, CategoryViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute("categories", categoryViews);


        return "add-course";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("courseAddBindingModel") CourseAddBindingModel courseAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model,
                             Principal principal) throws IOException {
        // TODO Valid

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("courseAddBindingModel", courseAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.courseAddBindingModel", bindingResult);

            return "redirect:/courses/add";
        }

        String name = courseAddBindingModel.getName();
        String username1 = principal.getName();

//         Combination of course`s name and teacher-creator must be unique
        if (courseService.courseWithNameAndTeacher(name,username1)) {
            redirectAttributes.addFlashAttribute("courseAddBindingModel", courseAddBindingModel);
            redirectAttributes.addFlashAttribute("courseExistsError", true);

            return "redirect:/courses/add";
        }

        CourseAddServiceModel courseAddServiceModel = modelMapper.map(courseAddBindingModel, CourseAddServiceModel.class);
        courseService.addCourse(principal.getName(), courseAddServiceModel);

        // add category checkbox
        List<CategoryViewModel> categoryViews = categoryService
                .findAll()
                .stream()
                .map(csm -> modelMapper.map(csm, CategoryViewModel.class))
                .collect(Collectors.toList());
        model.addAttribute("categories", categoryViews);

        // Views for all categories
//        List<CourseViewModel> coursesViewModels = courseService
//                .findAll()
//                .stream()
//                .map(cs -> {
//                    CourseViewModel courseView = modelMapper.map(cs, CourseViewModel.class);
//                    if (courseView.getPrice().equals("0.00")) {
//                        courseView.setPrice("FREE");
//                    } else {
//                        courseView.setPrice(courseView.getPrice() + " лв.");
//                    }
//                    return courseView;
//                })
//                .collect(Collectors.toList());
//
//        model.addAttribute("courses", coursesViewModels);



        return "redirect:/home";
    }
}
