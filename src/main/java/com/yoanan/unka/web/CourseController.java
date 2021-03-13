package com.yoanan.unka.web;

import com.yoanan.unka.model.binding.CourseAddBindingModel;
import com.yoanan.unka.model.service.CourseAddServiceModel;
import com.yoanan.unka.model.service.CourseServiceModel;
import com.yoanan.unka.model.view.CategoryViewModel;
import com.yoanan.unka.model.view.CourseViewModel;
import com.yoanan.unka.service.CategoryService;
import com.yoanan.unka.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public String add(Model model, Principal principal) {
        if (!model.containsAttribute("courseAddBindingModel")) {
            model.addAttribute("courseAddBindingModel", new CourseAddBindingModel());
        }

        // add category checkbox and dropdown navigation

        dropdownNavigationCategory(model, categoryService, modelMapper);


        return "add-course";
    }

    static void dropdownNavigationCategory(Model model, CategoryService categoryService, ModelMapper modelMapper) {
        List<CategoryViewModel> categoryViews = categoryService
                .findAllCategories()
                .stream()
                .map(csm -> modelMapper.map(csm, CategoryViewModel.class))
                .collect(Collectors.toList());
        model.addAttribute("categories", categoryViews);
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("courseAddBindingModel") CourseAddBindingModel courseAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model,
                             Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("courseAddBindingModel", courseAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.courseAddBindingModel", bindingResult);

            return "redirect:/courses/add";
        }

        String name = courseAddBindingModel.getName();
        String username1 = principal.getName();

//         Combination of course`s name and teacher-creator must be unique
        if (courseService.courseWithNameAndTeacher(name, username1)) {
            redirectAttributes.addFlashAttribute("courseAddBindingModel", courseAddBindingModel);
            redirectAttributes.addFlashAttribute("courseExistsError", true);

            return "redirect:/courses/add";
        }

        CourseAddServiceModel courseAddServiceModel = modelMapper.map(courseAddBindingModel, CourseAddServiceModel.class);
        courseService.addCourse(principal.getName(), courseAddServiceModel);

        // add category checkbox and dropdown category navigation
//        List<CategoryViewModel> categoryViews = categoryService
//                .findAll()
//                .stream()
//                .map(csm -> modelMapper.map(csm, CategoryViewModel.class))
//                .collect(Collectors.toList());
//
//        model.addAttribute("categories", categoryViews);


        return "redirect:/courses/add";
    }



    @GetMapping("/all")
    public String all(Model model) {

        return findPaginate(1,"name", "asc", model);
    }

    @GetMapping("/all/{pageNo}")
    public String findPaginate(@PathVariable(value = "pageNo") int pageNo,
                               @RequestParam(value = "sortField") String sortField,
                               @RequestParam(value = "sortDir") String sortDir,
                               Model model) {
        int pageSize = 6;
        Page<CourseServiceModel> paginated = courseService
                .findPaginated(pageNo, pageSize, sortField, sortDir);


        List<CourseViewModel> coursesViewModels = paginated
                .getContent()
                .stream()
                .map(csm -> {
                    CourseViewModel courseView = modelMapper.map(csm, CourseViewModel.class);
                    if (courseView.getPrice().equals("0.00")) {
                        courseView.setPrice("FREE");
                    } else {
                        courseView.setPrice(courseView.getPrice() + " лв.");
                    }
                    return courseView;
                })
                .collect(Collectors.toList());

        model.addAttribute("courses", coursesViewModels);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", paginated.getTotalPages());
        model.addAttribute("totalItems", paginated.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        System.out.println();
        return "courses";
    }
}
