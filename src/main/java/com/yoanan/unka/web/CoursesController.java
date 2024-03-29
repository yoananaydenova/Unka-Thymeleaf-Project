package com.yoanan.unka.web;

import com.yoanan.unka.model.binding.CourseAddBindingModel;
import com.yoanan.unka.model.service.CourseAddServiceModel;
import com.yoanan.unka.model.service.CourseServiceModel;
import com.yoanan.unka.model.service.ProfileInformationServiceModel;
import com.yoanan.unka.model.view.CourseViewModel;
import com.yoanan.unka.model.view.LessonNameViewModel;
import com.yoanan.unka.model.view.ProfileInformationViewModel;
import com.yoanan.unka.service.*;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CoursesController {

    private final ModelMapper modelMapper;
    private final CourseService courseService;
    private final CategoryService categoryService;
    private final LessonService lessonService;
    private final EnrolledCoursesService enrolledCoursesService;
    private final ProfileInformationService profileInformationService;

    public CoursesController(ModelMapper modelMapper, CourseService courseService, CategoryService categoryService, LessonService lessonService, EnrolledCoursesService enrolledCoursesService, ProfileInformationService profileInformationService) {
        this.modelMapper = modelMapper;
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.lessonService = lessonService;
        this.enrolledCoursesService = enrolledCoursesService;
        this.profileInformationService = profileInformationService;
    }


    @GetMapping("/add")
    public String add(Model model) {
        if (!model.containsAttribute("courseAddBindingModel")) {
            model.addAttribute("courseAddBindingModel", new CourseAddBindingModel());
        }

        // Adding category checkbox with GlobalControllerAdvice
        return "add-course";
    }


    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("courseAddBindingModel") CourseAddBindingModel courseAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal) throws IOException {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("courseAddBindingModel", courseAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.courseAddBindingModel", bindingResult);

            return "redirect:/courses/add";
        }

//         Combination of course`s name and teacher-creator must be UNIQUE
        if (courseService.courseWithNameAndTeacher(courseAddBindingModel.getName())) {
            redirectAttributes.addFlashAttribute("courseAddBindingModel", courseAddBindingModel);
            redirectAttributes.addFlashAttribute("courseExistsError", true);

            return "redirect:/courses/add";
        }

        CourseAddServiceModel courseAddServiceModel = modelMapper.map(courseAddBindingModel, CourseAddServiceModel.class);
        courseAddServiceModel.setTeacher(principal.getName());
        courseService.addCourse(courseAddServiceModel);

        return "redirect:/board";
    }


    @GetMapping(value = {"/all", "/all/{category}"})
    public String findAllPaginate(@PathVariable(value = "category") Optional<Long> category,
                                         @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                         @RequestParam(value = "sortField", defaultValue = "name") String sortField,
                                         @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                         Model model) {
        int pageSize = 6;

        Page<CourseServiceModel> paginated;
        // Category name ALL
        String categoryName = "ВСИЧКИ";

        if (category.isPresent()) {
            Long categoryId = category.get();
            // Current category name
            categoryName = categoryService.findById(categoryId).toUpperCase();
            // Courses by category
            paginated = courseService
                    .findByCategoryPaginated(categoryId, pageNo, pageSize, sortField, sortDir);
        } else {
            // All courses
            paginated = courseService
                    .findAllPaginated(pageNo, pageSize, sortField, sortDir);
        }


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

        model.addAttribute("categoryName", categoryName);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", paginated.getTotalPages());
        model.addAttribute("totalItems", paginated.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "courses";
    }

    @GetMapping("/my-courses")
    public String showMyEnrolledCourses(Model model) {

        List<CourseServiceModel> myEnrolledServiceCourses = enrolledCoursesService.findAllMyEnrolledCourses();

        List<CourseViewModel> myEnrolledCourses = myEnrolledServiceCourses
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

        model.addAttribute("courses", myEnrolledCourses);
        return "my-courses";
    }

    @GetMapping("/course/{id}")
    public String courseDetail(@PathVariable Long id, Model model) {

        // Add button Adding in shopping cart

        CourseServiceModel courseServiceModel = courseService.findCourseById(id);
        CourseViewModel courseViewModel = modelMapper.map(courseServiceModel, CourseViewModel.class);

        model.addAttribute("courseViewModel", courseViewModel);


        // Add list of lessons
        List<LessonNameViewModel> lessons = lessonService.findAllLessonsByCourseId(id)
                .stream()
                .map(l -> modelMapper.map(l, LessonNameViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute("lessonsList", lessons);

        ProfileInformationServiceModel profileInformationServiceModel = profileInformationService.getProfileInformationByUserId(courseServiceModel.getTeacher().getId());
        model.addAttribute("profileInformation", modelMapper.map(profileInformationServiceModel, ProfileInformationViewModel.class));

        return "details-course";
    }

}
