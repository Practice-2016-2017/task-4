package com.roi.controller;


import com.roi.entity.*;
import com.roi.repository.*;
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
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    //Страница админа
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin");
        return model;
    }

    //Список студентов
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

    //Добавление студента

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
                                      @RequestParam("year") String yearName ) {
        ModelAndView model = new ModelAndView("add-student");
        Integer login=Integer.parseInt(loginStr);
        Year year=yearRepository.findByName(Integer.parseInt(yearName));
        Student student=new Student(login,password,name,year);
        studentRepository.save(student);

        String message = "Студент добавлен";
        model.addObject("message", message);
        return model;
    }

    //Редактирование студента

    @RequestMapping(value = {"/admin/studentsList/edit/{id}"}, method = RequestMethod.GET)
    public ModelAndView editStudentPage(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView();
        Student student=studentRepository.findOne(id);
        model.addObject("id",id);
        model.addObject("studentName",student.getName());
        model.addObject("login",student.getLogin());
        model.addObject("password", student.getPassword());
        model.addObject("year",student.getYear().getName().toString());
        model.setViewName("edit-student");
        return model;
    }

    @RequestMapping(value = {"/admin/studentsList/edit/{id}"}, method = RequestMethod.POST)
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

    @RequestMapping(value = {"/admin/studentsList/delete/{id}"}, method = RequestMethod.GET)
    public ModelAndView deleteStudentPage(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView();
        Student student=studentRepository.findOne(id);
        model.addObject("id",id);
        model.addObject("studentName",student.getName());
        model.addObject("login",student.getLogin());
        model.addObject("password", student.getPassword());
        model.addObject("year",student.getYear());
        model.setViewName("delete-student");
        return model;
    }



    @RequestMapping(value = {"/admin/studentsList/delete/{id}"}, method = RequestMethod.POST)
    public String deleteStudent(@PathVariable Integer id){
        Student student=studentRepository.findOne(id);
        markRepository.removeByStudent(student);
        studentRepository.removeById(id);
        return "redirect:/admin/studentsList";
    }


    //Список преподавателей
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

    //Добавление преподавателя

    @RequestMapping(value = {"/admin/teachersList/addTeacher"}, method = RequestMethod.GET)
    public ModelAndView addTeacherPage() {
        ModelAndView model = new ModelAndView();

        model.setViewName("add-teacher");
        return model;
    }

    @RequestMapping(value = {"/admin/teachersList/addTeacher"}, method = RequestMethod.POST)
    public ModelAndView addingTeacher(@RequestParam("teacherName") String name,
                                      @RequestParam("login") String loginStr,
                                      @RequestParam("password") String password
                                      ){
        ModelAndView model = new ModelAndView("add-teacher");
        Integer login=Integer.parseInt(loginStr);
        Teacher teacher=new Teacher(login,password,name);
        teacherRepository.save(teacher);
        String message = "Преподаватель добавлен";
        model.addObject("message", message);
        return model;
    }

    //Редактирование преподавателя

    @RequestMapping(value = {"/admin/teachersList/edit/{id}"}, method = RequestMethod.GET)
    public ModelAndView editTeacherPage(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView();
        Teacher teacher=teacherRepository.findOne(id);
        model.addObject("id",id);
        model.addObject("teacherName",teacher.getName());
        model.addObject("login",teacher.getLogin());
        model.addObject("password", teacher.getPassword());
        model.setViewName("edit-teacher");
        return model;
    }

    @RequestMapping(value = {"/admin/teachersList/edit/{id}"}, method = RequestMethod.POST)
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

    @RequestMapping(value = {"/admin/teachersList/delete/{id}"}, method = RequestMethod.GET)
    public ModelAndView deleteTeacherPage(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView();
        Teacher teacher=teacherRepository.findOne(id);
        model.addObject("id",id);
        model.addObject("teacherName",teacher.getName());
        model.addObject("login",teacher.getLogin());
        model.addObject("password", teacher.getPassword());
        model.setViewName("delete-teacher");
        return model;
    }

    @RequestMapping(value = {"/admin/teachersList/delete/{id}"}, method = RequestMethod.POST)
    public String deleteTeacher(@PathVariable Integer id){
        Teacher teacher=teacherRepository.findById(id);
        List<Subject> subjectList=subjectRepository.findByTeacher(teacher);

        for(Subject s: subjectList){
            s.setTeacher(null);
            subjectRepository.save(s);
        }
        teacherRepository.removeById(id);
        return "redirect:/admin/studentsList";
    }

    //Список предметов
    @RequestMapping(value = {"/admin/subjectsList"}, method = RequestMethod.GET)
    public ModelAndView subjectsListPage() {
        ModelAndView model = new ModelAndView();
        List<Subject> subjects =subjectRepository.findAll();
        Map<String, Object> allObjectSubjects = new HashMap<String, Object>();
        allObjectSubjects.put("allSubjects", subjects);
        model.addAllObjects(allObjectSubjects);
        model.setViewName("subjects-list");
        return model;
    }

    //Редактирование предмета

    @RequestMapping(value = {"/admin/subjectsList/edit/{id}"}, method = RequestMethod.GET)
    public ModelAndView editSubjectPage(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView();
        Subject subject=subjectRepository.findOne(id);
        model.addObject("id",id);
        model.addObject("subjectName",subject.getName());
        model.addObject("year",subject.getYear().getName());
        model.addObject("teacher",subject.getTeacher());

        List<Teacher> teachers =teacherRepository.findAll();
        Map<String, Object> allObjectTeacher = new HashMap<String, Object>();
        allObjectTeacher.put("allTeachers", teachers);
        model.addAllObjects(allObjectTeacher);

        model.setViewName("edit-subject");
        return model;
    }

    @RequestMapping(value = {"/admin/subjectsList/edit/{id}"}, method = RequestMethod.POST)
    public String editSubject(@PathVariable Integer id,
                              @RequestParam("subjectName") String name,
                              @RequestParam("year") String yearName,
                              @RequestParam("teacher") String idStr){

        Subject subject=subjectRepository.findOne(id);
        Year year=yearRepository.findByName(Integer.parseInt(yearName));

        Integer idTeacher=Integer.parseInt(idStr);
        if(idTeacher!=-1) {
            Teacher teacher = teacherRepository.findById(idTeacher);
            subject.setTeacher(teacher);
        }
        else {
            subject.setTeacher(null);
        }

        subject.setName(name);
        subject.setYear(year);

        subjectRepository.save(subject);
        return "redirect:/admin/subjectsList";
    }

    //Добавление предмета

    @RequestMapping(value = {"/admin/subjectsList/addSubject"}, method = RequestMethod.GET)
    public ModelAndView addSubjectPage() {
        ModelAndView model = new ModelAndView();

        List<Teacher> teachers =teacherRepository.findAll();
        Map<String, Object> allObjectTeacher = new HashMap<String, Object>();
        allObjectTeacher.put("allTeachers", teachers);
        model.addAllObjects(allObjectTeacher);

        model.setViewName("add-subject");
        return model;
    }

    @RequestMapping(value = {"/admin/subjectsList/addSubject"}, method = RequestMethod.POST)
    public ModelAndView addingSubject(@RequestParam("subjectName") String name,
                                      @RequestParam("year") String yearName,
                                      @RequestParam("teacher") String idTeacherStr ) {
        ModelAndView model = new ModelAndView("add-subject");
        Year year=yearRepository.findByName(Integer.parseInt(yearName));
        Integer idTeacher=Integer.parseInt(idTeacherStr);
        Teacher teacher=null;
        if(idTeacher!=-1){
            teacher=teacherRepository.findById(idTeacher);
        }
        Subject subject=new Subject(name,teacher,year);
        subjectRepository.save(subject);
        String message = "Предмет добавлен";
        model.addObject("message", message);
        return model;
    }

    //Удаление предмета

    @RequestMapping(value = {"/admin/subjectsList/delete/{id}"}, method = RequestMethod.GET)
    public ModelAndView deleteSubjectPage(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView();
        Subject subject=subjectRepository.findOne(id);
        model.addObject("id",id);
        model.addObject("subjectName",subject.getName());
        model.addObject("year", subject.getYear());
        model.addObject("teacher", subject.getTeacher());
        model.setViewName("delete-subject");
        return model;
    }

    @RequestMapping(value = {"/admin/subjectsList/delete/{id}"}, method = RequestMethod.POST)
    public String deleteSubject(@PathVariable Integer id){
        Subject subject=subjectRepository.findOne(id);
        markRepository.removeBySubject(subject);
        subjectRepository.removeById(id);
        return "redirect:/admin/subjectsList";
    }

}
