package ru.melnikov.springBoot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/auth")
    public String getLoginForm(){
        return "auth/MyLogin";
    }
}
