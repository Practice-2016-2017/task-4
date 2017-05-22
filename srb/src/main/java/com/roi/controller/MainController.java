package com.roi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class MainController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String start(Model model){
        return "index";
    }

    @RequestMapping(value = "/errorPage", headers = {"/teacher/**,/admin/**"},method = RequestMethod.GET)
    public ModelAndView errorPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("error");
        return model;
    }
}
