package ru.melnikov.springBoot.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.melnikov.springBoot.security.PersonUserDetails;


@Controller
public class TestController {

    @GetMapping("/hello")
    public String first(Model model){
        //Получаем доступ к об Authentication, который хранит в себе Credentials и Principal (данные)
        // получаем из потока текущего пользователя с помощью SecurityContextHolder
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       //Получаем Principal и кастим их к классу-обёртке PersonUserDetails у которого есть метод
        //получения имени (также можем получить сам объект-модель PersonUser)
        PersonUserDetails personUserDetails = (PersonUserDetails) authentication.getPrincipal();
        model.addAttribute("name",personUserDetails.getUsername());
        return "test/hello";
    }
}
