package com.roi.controller.admin;


import com.roi.entity.Subject;
import com.roi.entity.Teacher;
import com.roi.repository.*;
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
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;


    //Список преподавателей
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView teachersListPage() {
        ModelAndView model = new ModelAndView();
        List<Teacher> teachers =teacherRepository.findAll();
        Map<String, Object> allObjectTeacher = new HashMap<String, Object>();
        allObjectTeacher.put("allTeachers", teachers);
        model.addAllObjects(allObjectTeacher);
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
                                      @RequestParam("password") String password
    ){
        ModelAndView model = new ModelAndView("admin-page/teacher-service/add-teacher");
        Integer login=Integer.parseInt(loginStr);
        Teacher teacher=new Teacher(login,password,name);
        teacherRepository.save(teacher);
        String message = "Преподаватель добавлен";
        model.addObject("message", message);
        return model;
    }

    //Редактирование преподавателя

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.GET)
    public ModelAndView editTeacherPage(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView();
        Teacher teacher=teacherRepository.findOne(id);
        model.addObject("id",id);
        model.addObject("teacherName",teacher.getName());
        model.addObject("login",teacher.getLogin());
        model.addObject("password", teacher.getPassword());
        model.setViewName("admin-page/teacher-service/edit-teacher");
        return model;
    }

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.POST)
    public String editTeacher(@PathVariable Integer id,
                              @RequestParam("teacherName") String name,
                              @RequestParam("login") String loginStr,
                              @RequestParam("password") String password) {
        Integer login=Integer.parseInt(loginStr);
        Teacher teacher=teacherRepository.findOne(id);
        teacher.setLogin(login);
        teacher.setName(name);
        teacher.setPassword(password);
        teacherRepository.save(teacher);
        return "redirect:/admin/teachersList";
    }

    //Удаление преподавателя

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.GET)
    public ModelAndView deleteTeacherPage(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView();
        Teacher teacher=teacherRepository.findOne(id);
        model.addObject("id",id);
        model.addObject("teacherName",teacher.getName());
        model.addObject("login",teacher.getLogin());
        model.addObject("password", teacher.getPassword());
        model.setViewName("admin-page/teacher-service/delete-teacher");
        return model;
    }

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.POST)
    public String deleteTeacher(@PathVariable Integer id){
        Teacher teacher=teacherRepository.findById(id);
        List<Subject> subjectList=subjectRepository.findByTeacher(teacher);

        for(Subject s: subjectList){
            s.setTeacher(null);
            subjectRepository.save(s);
        }
        teacherRepository.removeById(id);
        return "redirect:/admin/teachersList";
    }
}
