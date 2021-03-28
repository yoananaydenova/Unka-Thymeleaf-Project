package com.yoanan.unka.web;

import com.yoanan.unka.model.binding.ExerciseSolutionAddBindingModel;
import com.yoanan.unka.model.binding.SolutionAddBindingModel;
import com.yoanan.unka.model.service.ChartServiceModel;
import com.yoanan.unka.model.service.ExerciseServiceModel;
import com.yoanan.unka.model.service.SolutionServiceModel;
import com.yoanan.unka.model.view.CourseNameViewModel;
import com.yoanan.unka.model.view.ExerciseNameViewModel;
import com.yoanan.unka.model.view.ExerciseViewModel;
import com.yoanan.unka.model.view.LessonNameViewModel;
import com.yoanan.unka.service.*;
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
    private final SolutionService solutionService;
    private final ChartService chartService;
    private final ModelMapper modelMapper;

    public ExerciseController(LessonService lessonService, ExerciseService exerciseService, CourseService courseService, SolutionService solutionService, ChartService chartService, ModelMapper modelMapper) {
        this.lessonService = lessonService;
        this.exerciseService = exerciseService;
        this.courseService = courseService;
        this.solutionService = solutionService;
        this.chartService = chartService;
        this.modelMapper = modelMapper;
    }


    // TODO /add page with AJAX to select courses and lesson from them
    @GetMapping("/add")
    public String add(@RequestParam(value = "courseId", required = false) Long courseId,
                      @RequestParam(value = "lessonId", required = false) Long lessonId,
                      @ModelAttribute("courseId") Long courseIdModel,
                      Model model) {


        if (!model.containsAttribute("exerciseSolutionAddBindingModel")) {
            model.addAttribute("exerciseSolutionAddBindingModel", new ExerciseSolutionAddBindingModel());
        }
        model.addAttribute("selectedLesson", lessonId);


        if (courseId == null) {
            courseId = courseIdModel;
        }

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


        return "add-exercise-solution";
    }


    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("exerciseSolutionAddBindingModel") ExerciseSolutionAddBindingModel exerciseSolutionAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @ModelAttribute("courseId") Long courseIdModel) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("exerciseSolutionAddBindingModel", exerciseSolutionAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.exerciseSolutionAddBindingModel", bindingResult);

            redirectAttributes.addFlashAttribute("courseId", courseIdModel);
            redirectAttributes.addFlashAttribute("selectedLesson", exerciseSolutionAddBindingModel.getLessonId());

            return "redirect:/exercises/add";
        }

        //         The title of the exercise must be UNIQUE for the given lesson
        if (exerciseService.isUniqueExerciseWithNameAndLessonId(exerciseSolutionAddBindingModel.getTitle(),
                exerciseSolutionAddBindingModel.getLessonId())) {
            redirectAttributes.addFlashAttribute("exerciseSolutionAddBindingModel", exerciseSolutionAddBindingModel);
            redirectAttributes.addFlashAttribute("exerciseExistsError", true);

            redirectAttributes.addFlashAttribute("courseId", courseIdModel);
            redirectAttributes.addFlashAttribute("selectedLesson", exerciseSolutionAddBindingModel.getLessonId());

            return "redirect:/exercises/add";
        }


        // Save exercise in DB
        ExerciseServiceModel exerciseServiceModel = modelMapper.
                map(exerciseSolutionAddBindingModel, ExerciseServiceModel.class);
        ExerciseServiceModel newExerciseServiceModel = exerciseService.addExercise(exerciseServiceModel);

        // Save solution in DB
        // check if debit and credit id are real
        ChartServiceModel debitChartByIdOf = chartService.findByIdOfChart(exerciseSolutionAddBindingModel.getDebitChartId());
        ChartServiceModel creditChartByIdOf = chartService.findByIdOfChart(exerciseSolutionAddBindingModel.getCreditChartId());

        SolutionServiceModel solutionServiceModel = modelMapper.map(exerciseSolutionAddBindingModel, SolutionServiceModel.class);
        solutionServiceModel.setExercise(newExerciseServiceModel);

        solutionService.addSolution(solutionServiceModel);

        return "redirect:/lessons/" + exerciseSolutionAddBindingModel.getLessonId();
    }

    @GetMapping("/{id}")
    public String exerciseDetail(@PathVariable Long id, Model model) {

        if (!model.containsAttribute("solutionAddBindingModel")) {
            model.addAttribute("solutionAddBindingModel", new SolutionAddBindingModel());
        }

        ExerciseViewModel exerciseViewModel = modelMapper.map(exerciseService.findExerciseById(id), ExerciseViewModel.class);
        model.addAttribute("exerciseViewModel", exerciseViewModel);

        // list of exercise
        List<ExerciseNameViewModel> exercisesNameList = exerciseService
                .findAllExercisesByLessonId(exerciseViewModel.getLesson().getId())
                .stream()
                .map(es -> modelMapper.map(es, ExerciseNameViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute("exercisesNameList", exercisesNameList);

            //  TODO find better way for get index
        ExerciseNameViewModel exerciseNameViewModel = exercisesNameList.stream()
                .filter(ex -> ex.getId().equals(exerciseViewModel.getId())).findFirst()
                .orElseThrow(() -> new IllegalStateException("Not found index of current exercise view!"));
        int indexOfCurrentExercise = exercisesNameList.indexOf(exerciseNameViewModel);

        // index of current index
        model.addAttribute("indexOfCurrentExercise", indexOfCurrentExercise);

        // variable for visualization of buttons for teacher
        boolean isTeacherOfCourse = courseService.isTeacherOfCourseByCourseId(exerciseViewModel.getLesson().getCourse().getId());
        model.addAttribute("isTeacherOfCourse", isTeacherOfCourse);

        // TODO variable for button to next exercise if exist


        return "details-exercise-solution";
    }


    @PostMapping("/solution")
    public String exerciseSolution(@Valid @ModelAttribute("solutionAddBindingModel") SolutionAddBindingModel solutionAddBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        System.out.println();
        redirectAttributes.addFlashAttribute("solutionAddBindingModel", solutionAddBindingModel);

        // check for error in biding model
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.solutionAddBindingModel", bindingResult);
            return "redirect:/exercises/" + solutionAddBindingModel.getExerciseId();
        }

        // check if debit and credit id are real, if input charts are the same, value are the same
        boolean isTrueSolution = solutionService.verificationSolution(modelMapper.map(solutionAddBindingModel, SolutionServiceModel.class));
        if (!isTrueSolution) {
            // message "Wrong! Try again!"
            redirectAttributes.addFlashAttribute("isFalseSolutionMessage", true);
        } else {
            // message "Well done!"
            redirectAttributes.addFlashAttribute("isTrueSolutionMessage", true);
        }

        return "redirect:/exercises/" + solutionAddBindingModel.getExerciseId();

    }

}
