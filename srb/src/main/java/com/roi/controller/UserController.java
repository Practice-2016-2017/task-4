package com.roi.controller;


import com.roi.entity.Mark;
import com.roi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class UserController {
     @Autowired
     private UserService userService;

     @RequestMapping(value = {"/student"}, method = RequestMethod.GET)
     public ModelAndView studentPage(Principal principal) {
        ModelAndView model = new ModelAndView();
        String name = principal.getName();
        String userName=userService.findByNameStudent(name).getName();
        model.addObject("fullName", userName);
        model.setViewName("student");
        return model;
    }

    @RequestMapping(value = {"/teacher"}, method = RequestMethod.GET)
    public ModelAndView teacherPage(Principal principal) {
        ModelAndView model = new ModelAndView();
        String name = principal.getName();
        String userName=userService.findByNameTeacher(name).getName();
        model.addObject("fullName", userName);
        model.setViewName("teacher");
        return model;
    }
    @RequestMapping(value = {"/student/marks"}, method = RequestMethod.GET)
    public ModelAndView marksPage(Principal principal) {
        ModelAndView model = new ModelAndView();
        String name = principal.getName();
        List<Mark> marks=userService.getMarks(userService.findByNameStudent(name));
        Map<String,Object> allObjectMark = new HashMap<String,Object>();
        allObjectMark.put("allMarks", marks);
        model.addAllObjects(allObjectMark);
        model.setViewName("marks");
        return model;
    }
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public ModelAndView adminPage(Principal principal) {
        ModelAndView model = new ModelAndView();

        model.setViewName("admin");
        return model;
    }
}
