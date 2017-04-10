package com.roi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {


    @RequestMapping(value = {"/", "/login**"}, method = {RequestMethod.GET})
    public ModelAndView welcomePage() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Student Record Book");
        model.addObject("message", "Welcome Page !");
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = {"/student**"}, method = RequestMethod.GET)
    public ModelAndView studentPage() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Student page");
        model.addObject("message", "This is protected page - Only for Students!");
        model.setViewName("student");
        return model;
    }

    @RequestMapping(value = {"/teacher**"}, method = RequestMethod.GET)
    public ModelAndView teacherPage() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Teacher page");
        model.addObject("message", "This is protected page - Only for Teachers!");
        model.setViewName("teacher");
        return model;
    }

    @RequestMapping(value = {"/admin**"}, method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Admin page");
        model.addObject("message", "This is protected page - Only for Admin Users!");
        model.setViewName("admin");
        return model;
    }
}
