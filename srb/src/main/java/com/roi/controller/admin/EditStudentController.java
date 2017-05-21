package com.roi.controller.admin;

import com.roi.entity.Student;
import com.roi.entity.Year;
import com.roi.repository.*;
import com.roi.service.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("/admin/studentsList")
public class EditStudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private SessionUtils sessionUtils;

    //Список студентов
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView studentsListPage() {
        ModelAndView model = new ModelAndView();
        List<Student> students =studentRepository.findAll();
        Map<String, Object> allObjectStudent = new HashMap<String, Object>();
        allObjectStudent.put("allStudents", students);
        model.addAllObjects(allObjectStudent);
        model.setViewName("admin-page/student-service/students-list");
        return model;
    }

    //Добавление студента

    @RequestMapping(value = {"/addStudent"}, method = RequestMethod.GET)
    public ModelAndView addStudentPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin-page/student-service/add-student");
        return model;
    }


    @RequestMapping(value = {"/addStudent"}, method = RequestMethod.POST)
    public ModelAndView addingStudent(@RequestParam("studentName") String name,
                                      @RequestParam("login") String loginStr,
                                      @RequestParam("password") String password,
                                      @RequestParam("year") String yearName ) {
        ModelAndView model = new ModelAndView("admin-page/student-service/add-student");
        Integer login=Integer.parseInt(loginStr);
        Year year=yearRepository.findByName(Integer.parseInt(yearName));
        Student student=new Student(login,password,name,year);
        studentRepository.save(student);

        String message = "Студент добавлен";
        model.addObject("message", message);
        return model;
    }

    //Редактирование студента

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.GET)
    public ModelAndView editStudentPage(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView();
        Student student=studentRepository.findOne(id);
        model.addObject("id",id);
        model.addObject("studentName",student.getName());
        model.addObject("login",student.getLogin());
        model.addObject("password", student.getPassword());
        model.addObject("year",student.getYear().getName());
        model.setViewName("admin-page/student-service/edit-student");
        return model;
    }

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.POST)
    public String editStudent(@PathVariable Integer id,
                              @RequestParam("studentName") String name,
                              @RequestParam("login") String loginStr,
                              @RequestParam("password") String password,
                              @RequestParam("year") String yearName ){
        Integer login=Integer.parseInt(loginStr);
        Year year=yearRepository.findByName(Integer.parseInt(yearName));
        Student student=studentRepository.findOne(id);
        student.setLogin(login);
        student.setName(name);
        student.setPassword(password);
        student.setYear(year);
        studentRepository.save(student);
        return "redirect:/admin/studentsList";
    }


    //Удаление студента

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.GET)
    public ModelAndView deleteStudentPage(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView();
        Student student=studentRepository.findOne(id);
        model.addObject("id",id);
        model.addObject("studentName",student.getName());
        model.addObject("login",student.getLogin());
        model.addObject("password", student.getPassword());
        model.addObject("year",student.getYear());
        model.setViewName("admin-page/student-service/delete-student");
        return model;
    }



    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.POST)
    public String deleteStudent(@PathVariable Integer id){
        Student student=studentRepository.findOne(id);
        sessionUtils.expireUserSessions("st"+student.getLogin());
        markRepository.removeByStudent(student);
        studentRepository.removeById(id);
        return "redirect:/admin/studentsList";
    }

}