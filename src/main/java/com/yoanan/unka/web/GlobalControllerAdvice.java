package com.yoanan.unka.web;

import com.yoanan.unka.model.view.CategoryViewModel;
import com.yoanan.unka.service.CategoryService;
import com.yoanan.unka.service.ProfileInformationService;
import com.yoanan.unka.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final ProfileInformationService profileInformationService;
    private final UserService userService;

    public GlobalControllerAdvice(ModelMapper modelMapper, CategoryService categoryService, ProfileInformationService profileInformationService, UserService userService) {
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.profileInformationService = profileInformationService;
        this.userService = userService;
    }

    // add category checkbox in add-course and dropdown category navigation
    @ModelAttribute("categories")
    public List<CategoryViewModel> addingCategories() {
        return categoryService
                .findAllCategories()
                .stream()
                .map(csm -> modelMapper.map(csm, CategoryViewModel.class))
                .collect(Collectors.toList());
    }

    // Hide menu button become teacher
    @ModelAttribute("isTeacher")
    public boolean isTeacher(){
       return userService.currentUserIsTeacher();
    }

    // Hide menu button for admin function in session
    @ModelAttribute("isAdmin")
    public boolean isAdmin(){
        return userService.currentUserIsAdmin();
    }


}
