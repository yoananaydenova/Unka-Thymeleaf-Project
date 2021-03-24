package com.yoanan.unka.web;

import com.yoanan.unka.model.binding.ExerciseSolutionAddBindingModel;
import com.yoanan.unka.model.service.ExerciseServiceModel;
import com.yoanan.unka.model.view.CourseNameViewModel;
import com.yoanan.unka.model.view.LessonNameViewModel;
import com.yoanan.unka.service.CourseService;
import com.yoanan.unka.service.ExerciseService;
import com.yoanan.unka.service.LessonService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {

    private final LessonService lessonService;
    private final ExerciseService exerciseService;
    private final CourseService courseService;
    private final ModelMapper modelMapper;

    public ExerciseController(LessonService lessonService, ExerciseService exerciseService, CourseService courseService, ModelMapper modelMapper) {
        this.lessonService = lessonService;
        this.exerciseService = exerciseService;
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }


    // TODO /add page with AJAX
    @GetMapping("/add")
    public String add(@RequestParam(value = "courseId", required = false) Long courseId,
                      @RequestParam(value = "lessonId", required = false) Long lessonId,
                      Model model) {

        if (!model.containsAttribute("exerciseSolutionAddBindingModel")) {
            model.addAttribute("exerciseSolutionAddBindingModel", new ExerciseSolutionAddBindingModel());
        }
        if (courseId != null) {

            CourseNameViewModel courseName =
                    modelMapper.map(courseService.findCourseById(courseId), CourseNameViewModel.class);

            model.addAttribute("courseName", courseName);

            List<LessonNameViewModel> lessonsList =
                    lessonService
                            .findAllLessonsByCourseId(courseId)
                            .stream()
                            .map(lesson -> modelMapper.map(lesson, LessonNameViewModel.class))
                            .collect(Collectors.toList());

            model.addAttribute("lessonsList", lessonsList);
        }


        model.addAttribute("selectedLesson", lessonId != null ? lessonId : 0);

        // Adding solutions part
        return "add-exercise-solution";
    }


    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("exerciseSolutionAddBindingModel") ExerciseSolutionAddBindingModel exerciseSolutionAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("exerciseSolutionAddBindingModel", exerciseSolutionAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.exerciseSolutionAddBindingModel", bindingResult);
            return "redirect:/exercises/add";
        }

        //         The title of the exercise must be UNIQUE for the given lesson

        if (exerciseService.isUniqueExerciseWithNameAndLessonId(exerciseSolutionAddBindingModel.getTitle(),
                exerciseSolutionAddBindingModel.getLessonId())) {
            redirectAttributes.addFlashAttribute("exerciseSolutionAddBindingModel", exerciseSolutionAddBindingModel);
            redirectAttributes.addFlashAttribute("exerciseExistsError", true);

            return "redirect:/exercises/add";
        }
        System.out.println();

        // Save exercise in DB
        ExerciseServiceModel exerciseServiceModel = modelMapper.map(exerciseSolutionAddBindingModel, ExerciseServiceModel.class);
        exerciseService.addExercise(exerciseServiceModel);

        //TODO Save solution in DB

        return "redirect:/lessons/" + exerciseSolutionAddBindingModel.getLessonId();
    }
}
