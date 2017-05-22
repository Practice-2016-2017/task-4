package com.roi.controller.admin;


import com.roi.controller.MainController;
import com.roi.entity.Subject;
import com.roi.entity.Teacher;
import com.roi.repository.*;
import com.roi.service.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/teachersList")
public class EditTeacherController {

    @Autowired
    private MainController mainController;

    @Autowired
    private SessionUtils sessionUtils;


    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private String ERROR_MESSAGE=null;

    //Список преподавателей
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView teachersListPage() {
        ModelAndView model = new ModelAndView();
        List<Teacher> teachers =teacherRepository.findAll();
        model.addObject("allTeachers", teachers);
        model.setViewName("admin-page/teacher-service/teachers-list");
        return model;
    }

    //Добавление преподавателя

    @RequestMapping(value = {"/addTeacher"}, method = RequestMethod.GET)
    public ModelAndView addTeacherPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin-page/teacher-service/add-teacher");
        return model;
    }

    @RequestMapping(value = {"/addTeacher"}, method = RequestMethod.POST)
    public ModelAndView addingTeacher(@RequestParam("teacherName") String name,
                                      @RequestParam("login") String loginStr,
                                      @RequestParam("password") String password){
        ModelAndView model = new ModelAndView("admin-page/teacher-service/add-teacher");
        String message=null;
        String error=null;
        Integer login=Integer.parseInt(loginStr);
        if(teacherRepository.findByLogin(login)==null){
            Teacher teacher=new Teacher(login,password,name);
            teacherRepository.save(teacher);
            message = "Преподаватель добавлен";
        } else{
            error="Такой логин уже существует!";
        }
        model.addObject("message", message);
        model.addObject("error", error);
        return model;
    }

    //Редактирование преподавателя

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.GET)
    public ModelAndView editTeacherPage(@PathVariable Integer id) {
        if(teacherRepository.exists(id)) {
            ModelAndView model = new ModelAndView();
            Teacher teacher = teacherRepository.findOne(id);
            model.addObject("teacher", teacher);
            model.addObject("error", ERROR_MESSAGE);
            ERROR_MESSAGE = null;
            model.setViewName("admin-page/teacher-service/edit-teacher");
            return model;
        }else return mainController.errorPage();
    }

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.POST)
    public String editTeacher(@PathVariable Integer id,
                              @RequestParam("teacherName") String name,
                              @RequestParam("login") String loginStr,
                              @RequestParam("password") String password) {
        if(teacherRepository.exists(id)) {
            Integer login = Integer.parseInt(loginStr);
            Teacher teacher = teacherRepository.findOne(id);
            boolean enable = teacher.getLogin().equals(login) || teacherRepository.findByLogin(login) == null;
            if (enable) {
                teacher.setLogin(login);
                teacher.setName(name);
                teacher.setPassword(password);
                teacherRepository.save(teacher);
                return "redirect:/admin/teachersList";
            } else {
                ERROR_MESSAGE = "Такой логин уже существует!";
                return "redirect:/admin/teachersList/edit/{id}";
            }
        }else return "error";
    }

    //Удаление преподавателя

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.GET)
    public ModelAndView deleteTeacherPage(@PathVariable Integer id) {
        if(teacherRepository.exists(id)) {
            ModelAndView model = new ModelAndView();
            Teacher teacher = teacherRepository.findOne(id);
            model.addObject("teacher", teacher);
            model.setViewName("admin-page/teacher-service/delete-teacher");
            return model;
        }else return mainController.errorPage();
    }

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.POST)
    public String deleteTeacher(@PathVariable Integer id) {
        if (teacherRepository.exists(id)) {
            Teacher teacher = teacherRepository.findById(id);
            sessionUtils.expireUserSessions("te" + teacher.getLogin());
            List<Subject> subjectList = subjectRepository.findByTeacher(teacher);
            for (Subject s : subjectList) {
                s.setTeacher(null);
                subjectRepository.save(s);
            }
            teacherRepository.removeById(id);
            return "redirect:/admin/teachersList";
        }else return "error";
    }
}
