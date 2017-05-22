package com.roi.controller;

import com.roi.entity.Mark;
import com.roi.entity.Student;
import com.roi.entity.Subject;
import com.roi.entity.Year;
import com.roi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class StudentController {
    @Autowired
    private UserService userService;

    //Страница студента с ссылками на оценки по курсам, на которых учился студент (<= текущего курса)
    @RequestMapping(value = {"/student"}, method = RequestMethod.GET)
    public ModelAndView studentPage(Principal principal) {
        ModelAndView model = new ModelAndView();
        Student student= userService.findByLoginStudent(principal.getName());
        model.addObject("student", student);
        model.setViewName("student-page/student");
        return model;
    }

    //Оценки, полученные на курсе
    @RequestMapping(value = {"/student/marks/{year}"}, method = RequestMethod.GET)
    public ModelAndView studentMarksPage(Principal principal, @PathVariable Integer year) {
        ModelAndView model = new ModelAndView();
        String name=principal.getName();
        List<Mark> marks=userService.getStudentMarks(userService.findByLoginStudent(name));
        List<Mark> marksYear=new ArrayList<Mark>();

        for(Mark mark:marks){
         Subject subject=mark.getSubject();
         if (subject.getYear().getName().equals(year)){
             marksYear.add(mark);
         }
        }
        model.addObject("allMarks", marksYear);
        model.addObject("year", year);
        model.addObject("fullName", name);
        model.setViewName("student-page/students-marks");
        return model;
    }
}
