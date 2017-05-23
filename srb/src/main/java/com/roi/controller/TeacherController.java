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
    private StudentRepository studentRepository;

    @Autowired
    private MarkRepository markRepository;

    private String ERROR_MESSAGE=null;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date dateUtil;

    //Страница учителя с ссылками на предметы, которые он ведет
    @RequestMapping(value = {"/teacher"})
    public ModelAndView teacherPage(Principal principal) {
            ModelAndView model = new ModelAndView();
            Teacher teacher = userService.findByLoginTeacher(principal.getName());
            List<Subject> subjects = userService.getTeacherSubjects(teacher);
            model.addObject("allSubjects",subjects );
            model.addObject("teacher",teacher);
            model.setViewName("teacher-page/teacher");
            return model;
    }


    //Страница предмета с оценками по нему
    @RequestMapping(value = {"/teacher/{subjectId}"})
    public ModelAndView markPage(Principal principal,
                                 @PathVariable Integer subjectId) {
        if (!(subjectRepository.exists(subjectId)&&
            userService.ifSubjectContainTeacher(subjectId,principal.getName())))
               return mainController.errorPage();

            ModelAndView model = new ModelAndView();
            Subject subject = subjectRepository.findOne(subjectId);
            List<Mark> markList = userService.getSubjectMarks(subject);
            List<Student> studentList = userService.getYearStudents(subject.getYear());
            model.addObject("allYearStudents", studentList);
            model.addObject("allMarks", markList);
            model.addObject("Subject", subject);
            model.addObject("error", ERROR_MESSAGE);
            ERROR_MESSAGE = null;
            model.setViewName("teacher-page/teachers-marks");
            return model;
    }

    //Добавление оценки по определенному предмету
    @RequestMapping(value = {"/teacher/{subjectId}/add-mark"}, method = RequestMethod.GET)
    public String addMark(Principal principal,
                          @PathVariable Integer subjectId,
                          @RequestParam("date") String dateStr,
                          @RequestParam("studentId") Integer studentId,
                          @RequestParam("mark") String markValueStr) {
        if (!(subjectRepository.exists(subjectId)&&
            userService.ifSubjectContainTeacher(subjectId,principal.getName())))
              return "error";
        if (!studentRepository.exists(studentId)) {
            ERROR_MESSAGE = "Этот студент удален! Перезагрузите страницу!";
            return "redirect:/teacher/{subjectId}";
        }
        try {
             SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
             dateUtil = format.parse(dateStr);
             Date dateSQL = new Date(dateUtil.getTime());
             Mark mark = new Mark(Integer.parseInt(markValueStr), dateSQL,  studentRepository.findOne(studentId), subjectRepository.findOne(subjectId));
             markRepository.save(mark);

        } catch (java.text.ParseException e) {
                e.printStackTrace();
        }
             return "redirect:/teacher/{subjectId}";
    }

    //Удаление оценки
    @RequestMapping(value = {"/teacher/{subjectId}/delete-mark/{id}"}, method = RequestMethod.GET)
    public String deleteMark(Principal principal,
                             @PathVariable Integer subjectId,
                             @PathVariable Integer id) {
        if (!(markRepository.exists(id)&&
            userService.ifSubjectContainTeacher(subjectId,principal.getName())&&
            userService.ifMarkOfSubject(id,subjectId)))
            return "error";
            markRepository.removeById(id);
            return "redirect:/teacher/{subjectId}";
    }

    //Редактирование определенной оценки
    @RequestMapping(value = {"/teacher/{subjectId}/edit-mark/{id}"}, method = RequestMethod.GET)
    public ModelAndView editMark(Principal principal,
                                 @PathVariable Integer subjectId,
                                 @PathVariable Integer id) {
        if (!(markRepository.exists(id)&&
            userService.ifSubjectContainTeacher(subjectId,principal.getName())&&
            userService.ifMarkOfSubject(id,subjectId)))
            return mainController.errorPage();

            ModelAndView model = new ModelAndView();
            Mark mark=markRepository.findOne(id);
            Subject subject=mark.getSubject();
            List<Student> studentList = studentRepository.findByYear(subject.getYear());
            model.addObject("allYearStudents", studentList);
            model.addObject("mark", mark);
            model.addObject("error", ERROR_MESSAGE);
            ERROR_MESSAGE = null;
            model.setViewName("teacher-page/edit-mark");
            return model;
    }

    @RequestMapping(value = {"/teacher/{subjectId}/edit-mark/{id}"}, method = RequestMethod.POST)
    public String editMARK(Principal principal,
                              @PathVariable Integer subjectId,
                              @PathVariable Integer id,
                              @RequestParam("date") String dateStr,
                              @RequestParam("studentId") Integer studentId,
                              @RequestParam("mark") String markValueStr) {
      if (!(markRepository.exists(id)&&
          userService.ifSubjectContainTeacher(subjectId,principal.getName())&&
          userService.ifMarkOfSubject(id,subjectId)))
          return "error";
      if (!studentRepository.exists(studentId)) {
          ERROR_MESSAGE = "Этот студент удален! Перезагрузите страницу!";
          return "redirect:/teacher/{subjectId}";
      }
       try {
           Mark mark = markRepository.findOne(id);
           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
           dateUtil = format.parse(dateStr);
           Date dateSQL = new Date(dateUtil.getTime());
           mark.setDate(dateSQL);
           mark.setStudent(studentRepository.findOne(studentId));
           mark.setValue(Integer.parseInt(markValueStr));
           markRepository.save(mark);
            } catch (java.text.ParseException e) {
           e.printStackTrace();
            }
        return "redirect:/teacher/{subjectId}";
    }
}