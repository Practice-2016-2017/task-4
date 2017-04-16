package com.roi.controller;

import com.roi.entity.Mark;
import com.roi.repository.SubjectRepository;
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
public class StudentController {
    @Autowired
    private UserService userService;

    @Autowired
    private SubjectRepository subjectRepository;

    @RequestMapping(value = {"/student"}, method = RequestMethod.GET)
    public ModelAndView studentPage(Principal principal) {
        ModelAndView model = new ModelAndView();

        String name = principal.getName();
        List<Mark> marks=userService.getStudentMarks(userService.findByLoginStudent(name));
        Map<String,Object> allObjectMark = new HashMap<String,Object>();
        allObjectMark.put("allMarks", marks);
        model.addAllObjects(allObjectMark);

        String userName=userService.findByLoginStudent(name).getName();
        model.addObject("fullName", userName);
        model.setViewName("student");
        return model;
    }
}
