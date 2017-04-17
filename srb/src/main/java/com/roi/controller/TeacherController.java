package com.roi.controller;

import com.roi.entity.*;
import com.roi.repository.MarkRepository;
import com.roi.repository.StudentRepository;
import com.roi.repository.SubjectRepository;
import com.roi.repository.YearRepository;
import com.roi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Controller
public class TeacherController {
    @Autowired
    private UserService userService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MarkRepository markRepository;

    @RequestMapping(value = {"/teacher"})
    public ModelAndView teacherPage(Principal principal) {
        ModelAndView model = new ModelAndView();

        String name = principal.getName();
        Teacher teacher=userService.findByLoginTeacher(name);
        List<Subject> subjects=userService.getTeacherSubjects(teacher);
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

            String yearOfSubject = subjectRepository.findOne(subjectId).year();
            model.addObject("yearOfSubject", yearOfSubject);

            Year year=yearRepository.findByName(Integer.parseInt(yearOfSubject));
            List<Student> studentList=userService.getYearStudents(year);
            Map<String, Object> allStudents = new HashMap<String, Object>();
            allStudents.put("allYearStudents", studentList);
            model.addAllObjects(allStudents);

            Subject subject=subjectRepository.findOne(subjectId);
            List<Mark> markList = userService.getSubjectMarks(subject);
            Map<String, Object> allSubjectMark = new HashMap<String, Object>();
            allSubjectMark.put("allMarks", markList);
            model.addAllObjects(allSubjectMark);
            model.setViewName("marks");
            return model;
        }
        else {
            ForbiddenException ex=new ForbiddenException();
            throw ex;
        }
    }

    @RequestMapping(value = {"/add-mark"}, method = RequestMethod.GET)
    public String addMark(@RequestParam("date") String dateStr,
                          @RequestParam("studentName") String studentName,
                          @RequestParam("mark") String markValue,
                          @RequestParam("subject") String subjectName,
                          @RequestParam("year") String yearStr ) {
        try {SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            java.util.Date dateUtil = format.parse(dateStr);
            Date dateSQL=new Date(dateUtil.getTime());
            Year year=yearRepository.findByName(Integer.parseInt(yearStr));
            Student student=studentRepository.findByName(studentName);
            Subject subject=subjectRepository.findByNameAndYear(subjectName,year);
            Mark mark =new Mark(Integer.parseInt(markValue), dateSQL,student,subject);
            markRepository.save(mark);
            } catch (java.text.ParseException e){
            e.printStackTrace();
        }return "redirect:/teacher/{id}/{subjectId}";
    }


    @RequestMapping(value = {"/add-subject"}, method = RequestMethod.GET)
    public String addSubject(Principal principal,@RequestParam("subjectName") String nameOfSubject,
                                 @RequestParam("year") String nameYear  ) {
        Year year=yearRepository.findByName(Integer.parseInt(nameYear));
        Teacher teacher=userService.findByLoginTeacher(principal.getName());
        Subject subject =new Subject(nameOfSubject,teacher,year);
        subjectRepository.save(subject);
        return "redirect:/teacher";
    }


}
