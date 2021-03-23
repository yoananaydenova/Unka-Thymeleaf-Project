package com.yoanan.unka.web;

import com.yoanan.unka.model.binding.LessonAddBindingModel;
import com.yoanan.unka.model.service.LessonAddServiceModel;
import com.yoanan.unka.model.service.LessonServiceModel;
import com.yoanan.unka.model.view.CourseNameViewModel;
import com.yoanan.unka.model.view.LessonViewModel;
import com.yoanan.unka.service.CourseService;
import com.yoanan.unka.service.LessonService;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;
   private final CourseService courseService;
    private final ModelMapper modelMapper;

    public LessonController(LessonService lessonService,CourseService courseService, ModelMapper modelMapper) {
        this.lessonService = lessonService;
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("coursesList")
    public List<CourseNameViewModel> addingCoursesNames() {
        return courseService
                .findAllCoursesCreatedByCurrentLoggedTeacher()
                .stream()
                .map(course ->modelMapper.map(course,CourseNameViewModel.class))
                .collect(Collectors.toList());

    }

    @GetMapping("/add")
    public String add(Model model) {
        if (!model.containsAttribute("lessonAddBindingModel")) {
            model.addAttribute("lessonAddBindingModel", new LessonAddBindingModel());
        }
        // Adding  with courses list with ModelAttribute
        return "add-lesson";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("lessonAddBindingModel") LessonAddBindingModel lessonAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal) throws IOException {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("lessonAddBindingModel", lessonAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.lessonAddBindingModel", bindingResult);

            return "redirect:/lessons/add";
        }

//         The title of the lesson must be UNIQUE for the given course
        if (lessonService.existLessonByTitleAndCourseId(lessonAddBindingModel.getTitle(), lessonAddBindingModel.getCourseId())) {
            redirectAttributes.addFlashAttribute("lessonAddBindingModel", lessonAddBindingModel);
            redirectAttributes.addFlashAttribute("lessonExistsError", true);

            return "redirect:/lessons/add";
        }

        LessonAddServiceModel lessonAddServiceModel = modelMapper.map(lessonAddBindingModel, LessonAddServiceModel.class);

        LessonServiceModel lessonServiceModel = lessonService.addLesson(lessonAddServiceModel);

//TODO redirect to view lesson - get id from lessonServiceModel
        return "redirect:/board";
    }


    @GetMapping("/{id}")
    public String courseDetail(@PathVariable Long id, Model model) {

        LessonViewModel lessonViewModel = modelMapper.map(lessonService.findLessonById(id), LessonViewModel.class);
        System.out.println();
        model.addAttribute("lessonViewModel", lessonViewModel);

        return "details-lesson";
    }
}
