package com.yoanan.unka.web;

import com.yoanan.unka.model.binding.CourseAddBindingModel;
import com.yoanan.unka.model.service.CourseServiceModel;
import com.yoanan.unka.model.service.ProfileInformationServiceModel;
import com.yoanan.unka.model.view.CourseNameViewModel;
import com.yoanan.unka.model.view.CourseViewModel;
import com.yoanan.unka.model.view.ProfileInformationViewModel;
import com.yoanan.unka.service.EnrolledCoursesService;
import com.yoanan.unka.service.ProfileInformationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
public class ProfileInformationController {

    private final ProfileInformationService profileInformationService;
    private final EnrolledCoursesService enrolledCoursesService;
    private final ModelMapper modelMapper;

    public ProfileInformationController(ProfileInformationService profileInformationService, EnrolledCoursesService enrolledCoursesService, ModelMapper modelMapper) {
        this.profileInformationService = profileInformationService;
        this.enrolledCoursesService = enrolledCoursesService;
        this.modelMapper = modelMapper;
    }

    // Profile page
//    TODO Edit profile
    @GetMapping
    public String profile(Model model) {

        ProfileInformationServiceModel profileInformationServiceModel = profileInformationService.getProfileInformation();
        model.addAttribute("profileInformation", modelMapper.map(profileInformationServiceModel, ProfileInformationViewModel.class));

        List<CourseNameViewModel> allMyEnrolledCourses = enrolledCoursesService
                .findAllMyEnrolledCourses()
                .stream()
                .map(course -> modelMapper.map(course, CourseNameViewModel.class))
                .collect(Collectors.toList());
        model.addAttribute("allMyEnrolledCourses", allMyEnrolledCourses);

        return "profile";
    }

    @PostMapping("/edit/photo")
    public String editPhoto(@ModelAttribute("img") MultipartFile img) throws IOException {
        System.out.println();
        if (!img.isEmpty()) {
            profileInformationService.saveProfileImage(img);
        }
        return "redirect:/profile";
    }
}
