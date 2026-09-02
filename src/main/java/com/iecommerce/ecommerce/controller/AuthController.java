package com.iecommerce.ecommerce.controller;


import com.iecommerce.ecommerce.entity.User;
import com.iecommerce.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user){
        userService.registerUser(user);
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

}
