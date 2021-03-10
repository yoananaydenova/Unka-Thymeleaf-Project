package com.yoanan.unka.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/become-teacher")
    public String becomeTeacher(){
        return "become-teacher";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
