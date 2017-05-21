package com.roi.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String loginPage(Model model, String error, String logout){
        if (error != null) {
            model.addAttribute("error", "Неправильный логин или пароль.");
        }

        if (logout != null) {
            model.addAttribute("message", "Вы вышли из аккаунта.");
        }

        return "login";
    }

}