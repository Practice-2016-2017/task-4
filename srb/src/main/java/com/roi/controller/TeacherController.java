package com.roi.controller;

import com.roi.entity.Mark;
import com.roi.entity.Subject;
import com.roi.entity.Year;
import com.roi.repository.SubjectRepository;
import com.roi.repository.YearRepository;
import com.roi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    private YearRepository yearRepository;

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
    public ModelAndView markPage(@PathVariable String id,
                                 @PathVariable Integer subjectId, Principal principal) {
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

    @RequestMapping(value = {"/add-subject"}, method = RequestMethod.GET)
    public String addSubjectPage(Principal principal,@RequestParam("subject") String nameOfSubject,
                                       @RequestParam("year") String nameYear  ) {
            Year year=yearRepository.findByName(Integer.parseInt(nameYear));
            Subject subject =new Subject(nameOfSubject,userService.findByLoginTeacher(principal.getName()),year);
            subjectRepository.save(subject);
            return "redirect:/teacher";
    }
}
