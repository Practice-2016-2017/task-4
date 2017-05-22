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
public class TeacherController {
    @Autowired
    private MainController mainController;

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

    //Страница учителя с ссылками на предметы, которые он ведет
    @RequestMapping(value = {"/teacher"})
    public ModelAndView teacherPage(Principal principal) {

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

            model.setViewName("teacher-page/teacher");
            return model;
    }

    //Страница предмета с оценками по нему
    @RequestMapping(value = {"/teacher/{subjectId}"})
    public ModelAndView markPage(Principal principal,
                                 @PathVariable Integer subjectId) {
        if (subjectRepository.exists(subjectId)&&
            userService.ifSubjectContainTeacher(subjectId,principal.getName())) {
            ModelAndView model = new ModelAndView();
            Subject subject = subjectRepository.findOne(subjectId);
            model.addObject("Subject", subject);
            String yearOfSubject = subject.getYear().getName().toString();

            Year year = yearRepository.findByName(Integer.parseInt(yearOfSubject));
            List<Student> studentList = userService.getYearStudents(year);
            Map<String, Object> allStudents = new HashMap<String, Object>();
            allStudents.put("allYearStudents", studentList);
            model.addAllObjects(allStudents);


            List<Mark> markList = userService.getSubjectMarks(subject);
            Map<String, Object> allSubjectMark = new HashMap<String, Object>();
            allSubjectMark.put("allMarks", markList);
            model.addAllObjects(allSubjectMark);
            model.setViewName("teacher-page/teachers-marks");
            return model;
        } else {
            return mainController.errorPage();
        }
    }

    //Добавление оценки по определенному предмету
    @RequestMapping(value = {"/teacher/{subjectId}/add-mark"}, method = RequestMethod.GET)
    public String addMark(Principal principal,
                          @PathVariable Integer subjectId,
                          @RequestParam("date") String dateStr,
                          @RequestParam("studentId") Integer studentId,
                          @RequestParam("mark") String markValueStr) {
        if (subjectRepository.exists(subjectId)&&
            userService.ifSubjectContainTeacher(subjectId,principal.getName())&&
            studentRepository.exists(studentId)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                dateUtil = format.parse(dateStr);
                Date dateSQL = new Date(dateUtil.getTime());
                Subject subject = subjectRepository.findOne(subjectId);

                Integer markValue = Integer.parseInt(markValueStr);
                Student student = studentRepository.findOne(studentId);

                Mark mark = new Mark(markValue, dateSQL, student, subject);
                markRepository.save(mark);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            return "redirect:/teacher/{subjectId}";
        } else {
            return "error";
        }
    }

    //Удаление оценки
    @RequestMapping(value = {"/teacher/{subjectId}/delete-mark/{id}"}, method = RequestMethod.GET)
    public String deleteMark(Principal principal,
                             @PathVariable Integer subjectId,
                             @PathVariable Integer id) {
        if (markRepository.exists(id)&&
            userService.ifSubjectContainTeacher(subjectId,principal.getName())&&
            userService.ifMarkOfSubject(id,subjectId)) {
            markRepository.removeById(id);
            return "redirect:/teacher/{subjectId}";
        } else {
            return "error";
        }
    }

    //Редактирование определенной оценки
    @RequestMapping(value = {"/teacher/{subjectId}/edit-mark/{id}"}, method = RequestMethod.GET)
    public ModelAndView editMark(Principal principal,
                                 @PathVariable Integer subjectId,
                                 @PathVariable Integer id) {
        if (markRepository.exists(id)&&
            userService.ifSubjectContainTeacher(subjectId,principal.getName())&&
            userService.ifMarkOfSubject(id,subjectId)) {

            ModelAndView model = new ModelAndView();
            Mark mark=markRepository.findOne(id);
            Subject subject=subjectRepository.findOne(subjectId);
            Student student=mark.getStudent();
            Date date=mark.getDate();
            Year year = subject.getYear();

            model.addObject("date", date);
            model.addObject("subject", subject);
            model.addObject("student", student);
            model.addObject("mark", mark);

            List<Student> studentList = userService.getYearStudents(year);
            Map<String, Object> allStudents = new HashMap<String, Object>();
            allStudents.put("allYearStudents", studentList);
            model.addAllObjects(allStudents);

            model.setViewName("teacher-page/edit-mark");
            return model;

           } else {
             return mainController.errorPage();
           }
    }

    @RequestMapping(value = {"/teacher/{subjectId}/edit-mark/{id}"}, method = RequestMethod.POST)
    public String editMARK(Principal principal,
                              @PathVariable Integer subjectId,
                              @PathVariable Integer id,
                              @RequestParam("date") String dateStr,
                              @RequestParam("studentId") Integer studentId,
                              @RequestParam("mark") String markValueStr) {
      if (markRepository.exists(id)&&
          userService.ifSubjectContainTeacher(subjectId,principal.getName())&&
          userService.ifMarkOfSubject(id,subjectId)) {
            try {
                Mark mark = markRepository.findOne(id);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                dateUtil = format.parse(dateStr);
                Date dateSQL = new Date(dateUtil.getTime());
                Integer markValue = Integer.parseInt(markValueStr);
                Student student = studentRepository.findOne(studentId);

                mark.setDate(dateSQL);
                mark.setStudent(student);
                mark.setValue(markValue);

                markRepository.save(mark);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            return "redirect:/teacher/{subjectId}";
      } else {
            return "error";
        }
    }
}