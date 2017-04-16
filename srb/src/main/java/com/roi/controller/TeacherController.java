package com.roi.controller;

import com.roi.entity.Mark;
import com.roi.entity.Subject;
import com.roi.repository.SubjectRepository;
import com.roi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class TeacherController {
    @Autowired
    private UserService userService;

    @Autowired
    private SubjectRepository subjectRepository;

    @RequestMapping(value = {"/teacher"})
    public ModelAndView teacherPage(Principal principal) {
        ModelAndView model = new ModelAndView();

        String name = principal.getName();
        List<Subject> subjects=userService.getTeacherSubjects(userService.findByLoginTeacher(name));
        Map<String,Object> allObjectSubject = new HashMap<String,Object>();
        allObjectSubject.put("allSubjects", subjects);
        model.addAllObjects(allObjectSubject);
        model.addObject("user",principal.getName());
        String userName=userService.findByLoginTeacher(name).getName();
        model.addObject("fullName", userName);

        model.setViewName("teacher");
        return model;
    }

    @RequestMapping(value = {"/teacher/{id}/{subjectId}"})
    public ModelAndView markPage(@PathVariable String id,@PathVariable Integer subjectId, Principal principal) {
        if(id.equals(principal.getName())) {
            ModelAndView model = new ModelAndView();
            String nameOfSubject = subjectRepository.findOne(subjectId).getName();
            model.addObject("Subject", nameOfSubject);

            List<Mark> markList = userService.getSubjectMarks(subjectRepository.findOne(subjectId));
            Map<String, Object> allObjectMark = new HashMap<String, Object>();
            allObjectMark.put("allMarks", markList);
            model.addAllObjects(allObjectMark);
            model.setViewName("marks");
            return model;
        }
        else {
            ForbiddenException ex=new ForbiddenException();
            throw ex;
        }
    }


}
