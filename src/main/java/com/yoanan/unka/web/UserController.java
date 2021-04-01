package com.yoanan.unka.web;

import com.yoanan.unka.model.binding.ProfileInformationAddBindingModel;
import com.yoanan.unka.model.binding.UserRegisterBindingModel;
import com.yoanan.unka.model.entity.enums.UserRole;
import com.yoanan.unka.model.service.ProfileInformationAddServiceModel;
import com.yoanan.unka.model.service.UserRegisterServiceModel;
import com.yoanan.unka.service.ProfileInformationService;
import com.yoanan.unka.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ProfileInformationService profileInformationService;

    public UserController(ModelMapper modelMapper, UserService userService, ProfileInformationService profileInformationService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.profileInformationService = profileInformationService;
    }

    @ModelAttribute("userRegisterBindingModel")
    public UserRegisterBindingModel createBindingModel() {
        return new UserRegisterBindingModel();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                            String username,
                                    RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("bad_credentials", true);
        redirectAttributes.addFlashAttribute("username", username);

        return "redirect:/users/login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerAndLoginUser(
            @Valid UserRegisterBindingModel userRegisterBindingModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

            return "redirect:/users/register";
        }

        if (userService.usernameExists(userRegisterBindingModel.getUsername())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("userExistsError", true);

            return "redirect:/users/register";
        }

        UserRegisterServiceModel userRegisterServiceModel =
                modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class);


        userService.registerAndLoginUser(userRegisterServiceModel);

        return "redirect:/home";
    }

    // Form to be teacher
    @GetMapping("/add-teacher")
    public String teach(Model model) {
        if (!model.containsAttribute("profileInformationAddBindingModel")) {
            model.addAttribute("profileInformationAddBindingModel", new ProfileInformationAddBindingModel());
        }
        return "add-teacher";
    }

    @PostMapping("/add-teacher")
    public String teachConfirm(@Valid @ModelAttribute("profileInformationAddBindingModel") ProfileInformationAddBindingModel profileInformationAddBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Principal principal) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("profileInformationAddBindingModel", profileInformationAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.profileInformationAddBindingModel", bindingResult);

            return "redirect:/users/add-teacher";
        }


        userService.addRole(principal.getName(), UserRole.TEACHER);
        profileInformationService.addInformation(modelMapper.map(profileInformationAddBindingModel, ProfileInformationAddServiceModel.class));

        return "redirect:/home";
    }


    @GetMapping("/all")
    public String listAllUsers (){
        return "all-users";
    }



}
