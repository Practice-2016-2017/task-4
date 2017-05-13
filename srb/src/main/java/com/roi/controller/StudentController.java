package com.roi.controller;

import com.roi.entity.Mark;
import com.roi.entity.Student;
import com.roi.entity.Subject;
import com.roi.entity.Year;
import com.roi.repository.SubjectRepository;
import com.roi.repository.YearRepository;
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


    @RequestMapping(value = {"/student"}, method = RequestMethod.GET)
    public ModelAndView studentPage(Principal principal) {
        ModelAndView model = new ModelAndView();
        String name = principal.getName();
        Student student= userService.findByLoginStudent(name);
        Year year=student.getYear();
        String userName = userService.findByLoginStudent(name).getName();
        model.addObject("fullName", userName);
        model.addObject("year", year.getName());
        model.setViewName("student");
        return model;
    }


    @RequestMapping(value = {"/student/marks/{year}"}, method = RequestMethod.GET)
    public ModelAndView studentMarksPage(Principal principal, @PathVariable Integer year) {
        ModelAndView model = new ModelAndView();
        String name = principal.getName();
        List<Mark> marks=userService.getStudentMarks(userService.findByLoginStudent(name));
        List<Mark> marksYear=new ArrayList<Mark>();

        for(Mark mark:marks){
         Subject subject=mark.getSubject();
         if (subject.year().equals(year.toString())){
             marksYear.add(mark);
         }
        }

        model.addObject("year", year);
        Map<String,Object> allObjectMark = new HashMap<String,Object>();
        allObjectMark.put("allMarks", marksYear);
        model.addAllObjects(allObjectMark);
        String userName=userService.findByLoginStudent(name).getName();
        model.addObject("fullName", userName);
        model.setViewName("students-marks");
        return model;
    }
}
