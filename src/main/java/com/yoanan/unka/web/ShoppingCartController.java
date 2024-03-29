package com.yoanan.unka.web;

import com.yoanan.unka.model.service.ShoppingCartServiceModel;
import com.yoanan.unka.model.view.ShoppingCartViewModel;
import com.yoanan.unka.service.EnrolledCoursesService;
import com.yoanan.unka.service.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {


    private final ShoppingCartService shoppingCartService;
    private final EnrolledCoursesService enrolledCoursesService;
    private final ModelMapper modelMapper;


    public ShoppingCartController(ShoppingCartService shoppingCartService, EnrolledCoursesService enrolledCoursesService, ModelMapper modelMapper) {
        this.shoppingCartService = shoppingCartService;
        this.enrolledCoursesService = enrolledCoursesService;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public String viewShoppingCart(Model model) {

        ShoppingCartServiceModel shoppingCartServiceModel = shoppingCartService.getShoppingCart();
        ShoppingCartViewModel shoppingCart =
                modelMapper.map(shoppingCartServiceModel, ShoppingCartViewModel.class);

        model.addAttribute("shoppingCart",shoppingCart);

        return "shopping-cart";
    }

    @PostMapping("/add/{id}")
    public String addCourseToCart(@PathVariable(value = "id") Long id) {
        shoppingCartService.addCourseInCart(id);
        // return my courses
        return "redirect:/courses/course/" + id;
    }

    // Data - payment details, credit card number
    @PostMapping("/checkout")
    public String checkout() {

        enrolledCoursesService.buyCourses();
        // return my courses
        return "redirect:/courses/my-courses";
    }

    @DeleteMapping("/{id}")
    public String deleteCourseFromCart(@PathVariable(value = "id") Long id){

        shoppingCartService.deleteCourseFromCart(id);

        return "redirect:/shopping-cart";
    }
}
