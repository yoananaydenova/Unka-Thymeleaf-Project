package com.yoanan.unka.web;

import com.yoanan.unka.model.binding.ProfileInformationChangeBindingModel;
import com.yoanan.unka.model.service.BaseServiceModel;
import com.yoanan.unka.model.service.ProfileInformationChangeServiceModel;
import com.yoanan.unka.model.service.ProfileInformationServiceModel;
import com.yoanan.unka.model.service.UserServiceModel;
import com.yoanan.unka.model.view.ProfileInformationViewModel;
import com.yoanan.unka.model.view.UserRoleViewModel;
import com.yoanan.unka.service.EnrolledCoursesService;
import com.yoanan.unka.service.ProfileInformationService;
import com.yoanan.unka.service.UserRoleService;
import com.yoanan.unka.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
public class ProfileInformationController {

    private final ProfileInformationService profileInformationService;
    private final EnrolledCoursesService enrolledCoursesService;
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProfileInformationController(ProfileInformationService profileInformationService, EnrolledCoursesService enrolledCoursesService, UserRoleService userRoleService, UserService userService, ModelMapper modelMapper) {
        this.profileInformationService = profileInformationService;
        this.enrolledCoursesService = enrolledCoursesService;
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    // Profile page
    @GetMapping
    public String profile(Model model) {

        ProfileInformationServiceModel profileInformationServiceModel =
                profileInformationService.getProfileInformation();

        ProfileInformationViewModel profileInformation = modelMapper.map(profileInformationServiceModel, ProfileInformationViewModel.class);
        List<Long> rolesIdOfUser = profileInformationServiceModel.getUser()
                .getRoles()
                .stream()
                .map(BaseServiceModel::getId)
                .collect(Collectors.toList());
        profileInformation.getUser().setRoleId(rolesIdOfUser);

        model.addAttribute("profileInformation", profileInformation);

        model.addAttribute("allMyEnrolledCoursesNumber", enrolledCoursesService
                .findAllMyEnrolledCoursesNumber());
        return "profile";
    }

    // Edit profile photo
    @PostMapping("/edit/photo")
    public String editPhoto(@ModelAttribute("img") MultipartFile img) throws IOException {

        //TODO validate miltipart file
        profileInformationService.saveProfileImage(img);
        return "redirect:/profile";
    }

    // See edit profile page
    @GetMapping("/edit/{id}")
    public String showEditProfile(@PathVariable Long id, Model model) {
        // Check is it current user or admin in service with getProfileInformationByUserId
        ProfileInformationServiceModel profileInformationServiceModel = profileInformationService.getEditProfileInformationByUserId(id);
        ProfileInformationChangeBindingModel profileInformationChangeBindingModel = modelMapper.map(profileInformationServiceModel, ProfileInformationChangeBindingModel.class);
        List<Long> rolesIdOfUser = profileInformationServiceModel.getUser()
                .getRoles()
                .stream()
                .map(BaseServiceModel::getId)
                .collect(Collectors.toList());

        profileInformationChangeBindingModel.setRolesId(rolesIdOfUser);
        profileInformationChangeBindingModel.setUserId(profileInformationServiceModel.getUser().getId());
        profileInformationChangeBindingModel.setFullName(profileInformationServiceModel.getUser().getFullName());
        profileInformationChangeBindingModel.setUsername(profileInformationServiceModel.getUser().getUsername());
        profileInformationChangeBindingModel.setTeachCourses(profileInformationServiceModel.getUser().getTeachCourses().size());

        profileInformationChangeBindingModel.setEnrolledCourses(enrolledCoursesService
                .findAllEnrolledCoursesNumberByUserId(id));

        model.addAttribute("profileInformationChangeBindingModel", profileInformationChangeBindingModel);

        return "edit-profile";

    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.setBindEmptyMultipartFiles(false);
    }

    // List with roles options for admin
    @ModelAttribute("listRoles")
    public List<UserRoleViewModel> listRolesForAdminChoice() {
        return userRoleService.findAllWithoutStudentAndRootAdmin().stream()
                .map(role -> modelMapper.map(role, UserRoleViewModel.class))
                .collect(Collectors.toList());
    }


    @PostMapping("/edit/{id}")
    public String editConfirm(@Valid @ModelAttribute("profileInformationChangeBindingModel")
                                      ProfileInformationChangeBindingModel profileInformationChangeBindingModel,
                              BindingResult bindingResult,
                              Model model,
                              @PathVariable Long id) throws IOException {


        if (bindingResult.hasErrors()) {
            profileInformationChangeBindingModel.setUserId(id);

            model.addAttribute(
                    "org.springframework.validation.BindingResult.profileInformationChangeBindingModel", bindingResult);

            return "edit-profile";
        }

        //check if loged user is OWNER of profile or is ADMIN
        boolean isAdmin = userService.currentUserIsAdmin();
        boolean isUserByIdIsLogedUser = userService.findUserByIdIsLogedUser(id);

        if (isAdmin || isUserByIdIsLogedUser) {
            //save change of user info
            UserServiceModel userServiceModel = modelMapper.map(profileInformationChangeBindingModel, UserServiceModel.class);
            userService.saveChangeFullName(userServiceModel);

            ProfileInformationChangeServiceModel profileInformationChangeServiceModel = modelMapper.map(profileInformationChangeBindingModel, ProfileInformationChangeServiceModel.class);
            profileInformationChangeServiceModel.setUserId(profileInformationChangeBindingModel.getUserId());
            profileInformationService.saveChangedInfo(profileInformationChangeServiceModel);

        }

        Long newRoleId = profileInformationChangeBindingModel.getNewRole();
        if (isAdmin && newRoleId != null) {
            //save change of role
            userService.changeRoleOfUser(id, newRoleId);

        }
        if(userService.currentUserIsAdmin()){
            return "redirect:/users/all";
        }

        return "redirect:/profile";
    }


}
