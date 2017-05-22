package com.roi.controller.admin;

import com.roi.controller.MainController;
import com.roi.entity.Student;
import com.roi.entity.Year;
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
@RequestMapping("/admin/studentsList")
public class EditStudentController {
    @Autowired
    private MainController mainController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private SessionUtils sessionUtils;

    private String ERROR_MESSAGE=null;

    //Список студентов
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView studentsListPage() {
        ModelAndView model = new ModelAndView();
        List<Student> students =studentRepository.findAll();
        model.addObject("allStudents", students);
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
        String message=null;
        String error=null;
        ModelAndView model = new ModelAndView("admin-page/student-service/add-student");
        Integer login=Integer.parseInt(loginStr);
        Year year=yearRepository.findByName(Integer.parseInt(yearName));
        if(studentRepository.findByLogin(login)==null){
            Student student=new Student(login,password,name,year);
            studentRepository.save(student);
            message = "Студент добавлен";
        } else{
            error="Такой логин уже существует!";
        }
        model.addObject("message", message);
        model.addObject("error", error);
        return model;
    }

    //Редактирование студента

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.GET)
    public ModelAndView editStudentPage(@PathVariable Integer id) {
        if(studentRepository.exists(id)) {
            ModelAndView model = new ModelAndView();
            Student student = studentRepository.findOne(id);
            model.addObject("student", student);
            model.addObject("error", ERROR_MESSAGE);
            ERROR_MESSAGE = null;
            model.setViewName("admin-page/student-service/edit-student");
            return model;
        }else return mainController.errorPage();
    }

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.POST)
    public String editStudent(@PathVariable Integer id,
                              @RequestParam("studentName") String name,
                              @RequestParam("login") String loginStr,
                              @RequestParam("password") String password,
                              @RequestParam("year") String yearName ){
        if(studentRepository.exists(id)) {
            Integer login = Integer.parseInt(loginStr);
            Year year = yearRepository.findByName(Integer.parseInt(yearName));
            Student student = studentRepository.findOne(id);
            boolean enable = student.getLogin().equals(login) || studentRepository.findByLogin(login) == null;
            if (enable) {
                student.setName(name);
                student.setYear(year);
                student.setPassword(password);
                studentRepository.save(student);
                return "redirect:/admin/studentsList";
            } else {
                ERROR_MESSAGE = "Такой логин уже существует!";
                return "redirect:/admin/studentsList/edit/{id}";
            }
        }else return "error";
    }


    //Удаление студента

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.GET)
    public ModelAndView deleteStudentPage(@PathVariable Integer id) {
        if(studentRepository.exists(id)) {
            ModelAndView model = new ModelAndView();
            Student student = studentRepository.findOne(id);
            model.addObject("student", student);
            model.setViewName("admin-page/student-service/delete-student");
            return model;
        } else return mainController.errorPage();
    }

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.POST)
    public String deleteStudent(@PathVariable Integer id){
        if (studentRepository.exists(id)) {
            Student student = studentRepository.findOne(id);
            sessionUtils.expireUserSessions("st" + student.getLogin());
            markRepository.removeByStudent(student);
            studentRepository.removeById(id);
            return "redirect:/admin/studentsList";
        }else return "error";
    }

}
