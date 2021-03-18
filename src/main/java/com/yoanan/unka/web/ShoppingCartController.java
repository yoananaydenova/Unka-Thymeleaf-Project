package com.yoanan.unka.web;

import com.yoanan.unka.model.view.CourseViewModel;
import com.yoanan.unka.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {


    private final UserService userService;

    public ShoppingCartController(UserService userService) {
        this.userService = userService;
    }

    // Navbar button
    @GetMapping
    public String viewShoppingCart(Model model, Principal principal) {

        List<CourseViewModel> courseViewModels = userService.listCoursesInCart(principal.getName());
        System.out.println();
        model.addAttribute("listCardCourses",courseViewModels);

        return "shopping-cart";
    }

    @PostMapping("/add/{id}")
    public String buyCourses(@PathVariable(value = "id") Long id,
                             Principal principal) {
        System.out.println();
        userService.addCourseInCart(principal.getName(), id);

        // return my courses
        return "redirect:/shopping-cart";
    }

    // Data - payment details, credit card number
    @GetMapping("/checkout")
    public String checkout() {

        // return my courses
        return "redirect:/home";
    }
}
