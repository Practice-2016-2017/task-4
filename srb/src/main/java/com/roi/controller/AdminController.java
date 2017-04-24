package com.roi.controller;


import com.roi.entity.Student;
import com.roi.entity.Teacher;
import com.roi.entity.Year;
import com.roi.repository.StudentRepository;
import com.roi.repository.TeacherRepository;
import com.roi.repository.YearRepository;
import com.roi.service.UserService;
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
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private YearRepository yearRepository;

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin");
        return model;
    }
    @RequestMapping(value = {"/admin/studentsList/addStudent"}, method = RequestMethod.GET)
    public ModelAndView addStudentPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("add-student");
        return model;
    }

    @RequestMapping(value = {"/admin/studentsList/addStudent"}, method = RequestMethod.POST)
    public ModelAndView addingStudent(@RequestParam("studentName") String name,
                                      @RequestParam("login") String loginStr,
                                      @RequestParam("password") String password,
                                      @RequestParam("year") String yearName )throws Exception {
        ModelAndView model = new ModelAndView("add-student");
        Integer login=Integer.parseInt(loginStr);
        Year year=yearRepository.findByName(Integer.parseInt(yearName));
        Student student=new Student(login,password,name,year);
        studentRepository.save(student);

        String message = "Студент добавлен";
        model.addObject("message", message);
        return model;
    }


    @RequestMapping(value = {"/admin/studentsList"}, method = RequestMethod.GET)
    public ModelAndView studentsListPage() {
        ModelAndView model = new ModelAndView();
        List<Student> students =studentRepository.findAll();
        Map<String, Object> allObjectStudent = new HashMap<String, Object>();
        allObjectStudent.put("allStudents", students);
        model.addAllObjects(allObjectStudent);
        model.setViewName("students-list");
        return model;
    }

    @RequestMapping(value = {"/admin/teachersList"}, method = RequestMethod.GET)
    public ModelAndView teachersListPage() {
        ModelAndView model = new ModelAndView();
        List<Teacher> teachers =teacherRepository.findAll();
        Map<String, Object> allObjectTeacher = new HashMap<String, Object>();
        allObjectTeacher.put("allTeachers", teachers);
        model.addAllObjects(allObjectTeacher);
        model.setViewName("teachers-list");
        return model;
    }

    @RequestMapping(value = {"/admin/teachersList/addTeacher"}, method = RequestMethod.GET)
    public ModelAndView addTeacherPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("add-teacher");
        return model;
    }

    @RequestMapping(value = {"/admin/teachersList/addStudent"}, method = RequestMethod.POST)
    public ModelAndView addingTeacher(@RequestParam("teacherName") String name,
                                      @RequestParam("login") String loginStr,
                                      @RequestParam("password") String password
                                      )throws Exception {
        ModelAndView model = new ModelAndView("add-teacher");
        Integer login=Integer.parseInt(loginStr);
        Teacher teacher=new Teacher(login,password,name);
        teacherRepository.save(teacher);
        String message = "Преподаватель добавлен";
        model.addObject("message", message);
        return model;
    }
}
