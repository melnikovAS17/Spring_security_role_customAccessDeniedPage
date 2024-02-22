package ru.melnikov.springBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.melnikov.springBoot.models.PersonUser;
import ru.melnikov.springBoot.services.RegistrationPersonService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationPersonService registrationPersonService;
    @Autowired
    public AuthController(RegistrationPersonService registrationPersonService) {
        this.registrationPersonService = registrationPersonService;
    }
    //Метод аутентификации
    @GetMapping()
    public String getLoginForm(){

        return "auth/MyLogin";
    }
    //Метод регистрации
    @GetMapping("/registration")
    public String getFormForRegistration(@ModelAttribute("person") PersonUser personUser){
        //@ModelAttribute - создаст пустой объект для формы
        // т е model.addAttribute("person", new PersonUser());
        return "auth/registrationForm";
    }
    @PostMapping("/registration")
    public String registrationForm(@ModelAttribute("person") PersonUser personUser){
        registrationPersonService.register(personUser);
        return "redirect:/auth";
    }
}
