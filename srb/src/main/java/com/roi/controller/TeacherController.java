package com.roi.controller;

import com.roi.entity.*;
import com.roi.repository.MarkRepository;
import com.roi.repository.StudentRepository;
import com.roi.repository.SubjectRepository;
import com.roi.repository.YearRepository;
import com.roi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = {"/teacher"})
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date dateUtil;

    @RequestMapping(value = {"/{login}"})
    public ModelAndView teacherPage(@PathVariable String login, Principal principal) {
        if (login.equals(principal.getName())) {

            ModelAndView model = new ModelAndView();
            String name = principal.getName();
            Teacher teacher = userService.findByLoginTeacher(name);
            List<Subject> subjects = userService.getTeacherSubjects(teacher);
            Map<String, Object> allObjectSubject = new HashMap<String, Object>();
            allObjectSubject.put("allSubjects", subjects);
            model.addAllObjects(allObjectSubject);

            model.addObject("user", principal.getName());
            String userName = userService.findByLoginTeacher(name).getName();
            model.addObject("fullName", userName);

            model.setViewName("teacher");
            return model;
        } else {
            throw new ForbiddenException();
        }

    }

    @RequestMapping(value = {"/{login}/{subjectId}"})
    public ModelAndView markPage(Principal principal,
                                 @PathVariable String login,
                                 @PathVariable Integer subjectId) {
        if (login.equals(principal.getName())) {
            ModelAndView model = new ModelAndView();
            Subject subject = subjectRepository.findOne(subjectId);
            model.addObject("Subject", subject);
            String yearOfSubject = subject.year();

            Year year = yearRepository.findByName(Integer.parseInt(yearOfSubject));
            List<Student> studentList = userService.getYearStudents(year);
            Map<String, Object> allStudents = new HashMap<String, Object>();
            allStudents.put("allYearStudents", studentList);
            model.addAllObjects(allStudents);


            List<Mark> markList = userService.getSubjectMarks(subject);
            Map<String, Object> allSubjectMark = new HashMap<String, Object>();
            allSubjectMark.put("allMarks", markList);
            model.addAllObjects(allSubjectMark);
            model.setViewName("marks");
            return model;
        } else {
            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = {"/{login}/{subjectId}/add-mark"}, method = RequestMethod.GET)
    public String addMark(Principal principal,
                          @PathVariable String login,
                          @PathVariable Integer subjectId,
                          @RequestParam("date") String dateStr,
                          @RequestParam("studentName") String studentName,
                          @RequestParam("mark") String markValueStr) {
        if (login.equals(principal.getName())) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                dateUtil = format.parse(dateStr);
                Date dateSQL = new Date(dateUtil.getTime());
                Subject subject = subjectRepository.findOne(subjectId);

                Integer markValue = Integer.parseInt(markValueStr);
                Student student = studentRepository.findByName(studentName);

                Mark mark = new Mark(markValue, dateSQL, student, subject);
                markRepository.save(mark);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            return "redirect:/teacher/{login}/{subjectId}";
        } else {
            throw new ForbiddenException();
        }
    }
}
