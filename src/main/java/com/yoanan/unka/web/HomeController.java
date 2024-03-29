package com.yoanan.unka.web;

import com.yoanan.unka.model.service.CourseServiceModel;
import com.yoanan.unka.model.view.CourseViewModel;
import com.yoanan.unka.service.CarouselService;
import com.yoanan.unka.service.CategoryService;
import com.yoanan.unka.service.CourseService;
import com.yoanan.unka.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final ModelMapper modelMapper;
    private final CourseService courseService;
    private final CategoryService categoryService;
    private final CarouselService carouselService;
    private final UserService userService;

    public HomeController(ModelMapper modelMapper, CourseService courseService, CategoryService categoryService, CarouselService carouselService, UserService userService) {
        this.modelMapper = modelMapper;
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.carouselService = carouselService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {

        List<CourseServiceModel> threeCoursesWithLowerPrice = courseService.findThreeCoursesWithLowerPrice();

        List<CourseViewModel> coursesViewModels = threeCoursesWithLowerPrice
                .stream()
                .map(course -> {
                    CourseViewModel courseView = modelMapper.map(course, CourseViewModel.class);
//                    courseView.setTeacher(course.getTeacher().getFullName());
                    if (courseView.getPrice().equals("0.00")) {
                        courseView.setPrice("FREE");
                    } else {
                        courseView.setPrice(courseView.getPrice() + " лв.");
                    }
                    return courseView;
                })
                .collect(Collectors.toList());

        model.addAttribute("courses", coursesViewModels);

        return "index";
    }


    //    "/board/{pageNo}"
    @GetMapping(value = {"/board", "/board/{category}"})
    public String teacherBoardPaginate(@PathVariable(value = "category") Optional<Long> category,
                                       @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                       @RequestParam(value = "sortField", defaultValue = "name") String sortField,
                                       @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                       Model model, Principal principal) {

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
                    .findAllByTeacherAndCategoryPaginated(categoryId, pageNo, pageSize, sortField, sortDir);
        } else {
            // All courses
            paginated = courseService
                    .findAllByTeacherPaginated(pageNo, pageSize, sortField, sortDir);
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

        return "board-courses";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("firstImg", carouselService.firstImage());
        model.addAttribute("secondImg", carouselService.secondImage());
        model.addAttribute("thirdImg", carouselService.thirdImage());

        model.addAttribute("fullName", userService.findFullName());
        return "home";
    }

    @GetMapping("/become-teacher")
    public String becomeTeacher() {
        return "become-teacher";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
