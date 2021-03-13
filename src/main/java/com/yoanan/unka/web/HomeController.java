package com.yoanan.unka.web;

import com.yoanan.unka.model.service.CourseServiceModel;
import com.yoanan.unka.model.view.CourseViewModel;
import com.yoanan.unka.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final ModelMapper modelMapper;
    private final CourseService courseService;

    public HomeController(ModelMapper modelMapper, CourseService courseService) {
        this.modelMapper = modelMapper;
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/board")
    public String teacherBoard(Model model, Principal principal){
        return teacherBoardPaginate(1,"name", "asc",  model, principal);
    }

    @GetMapping("/board/{pageNo}")
    public String teacherBoardPaginate(@PathVariable(value = "pageNo") int pageNo,
                               @RequestParam(value = "sortField") String sortField,
                               @RequestParam(value = "sortDir") String sortDir,
                               Model model, Principal principal){

//        List<CourseViewModel> coursesViewModels =
//                courseService
//                        .findByTeacherUsername(principal.getName())
//                        .stream()
//                        .map(cs -> {
//                            CourseViewModel courseView = modelMapper.map(cs, CourseViewModel.class);
//                            if (courseView.getPrice().equals("0.00")) {
//                                courseView.setPrice("FREE");
//                            } else {
//                                courseView.setPrice(courseView.getPrice() + " лв.");
//                            }
//                            return courseView;
//                        })
//                        .collect(Collectors.toList());

        int pageSize = 6;
        Page<CourseServiceModel> paginated = courseService
                .findByTeacherPaginated(principal.getName(),pageNo, pageSize, sortField, sortDir);

        List<CourseViewModel> coursesViewModels = paginated
                .getContent()
                .stream()
                .map(csm ->{
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

        model.addAttribute("courses", coursesViewModels);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", paginated.getTotalPages());
        model.addAttribute("totalItems", paginated.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "board-courses";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/become-teacher")
    public String becomeTeacher(){
        return "become-teacher";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
