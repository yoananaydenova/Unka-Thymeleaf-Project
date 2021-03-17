package com.yoanan.unka.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    // Navbar button
    @GetMapping
    public String cart() {
        return "cart";
    }

    @PostMapping
    public String buyCourse() {

        // return my courses
        return "cart";
    }

    // Data - payment details, credit card number
    @GetMapping("/checkout")
    public String checkout() {

        // return my courses
        return "redirect:/home";
    }
}
