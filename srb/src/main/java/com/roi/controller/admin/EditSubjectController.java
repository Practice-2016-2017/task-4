package com.roi.controller.admin;

import com.roi.entity.Subject;
import com.roi.entity.Teacher;
import com.roi.entity.Year;
import com.roi.repository.MarkRepository;
import com.roi.repository.SubjectRepository;
import com.roi.repository.TeacherRepository;
import com.roi.repository.YearRepository;
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
@RequestMapping("/admin/subjectsList")
public class EditSubjectController {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    //Список предметов
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView subjectsListPage() {
        ModelAndView model = new ModelAndView();
        List<Subject> subjects =subjectRepository.findAll();
        Map<String, Object> allObjectSubjects = new HashMap<String, Object>();
        allObjectSubjects.put("allSubjects", subjects);
        model.addAllObjects(allObjectSubjects);
        model.setViewName("admin-page/subject-service/subjects-list");
        return model;
    }

    //Редактирование предмета

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.GET)
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

        model.setViewName("admin-page/subject-service/edit-subject");
        return model;
    }

    @RequestMapping(value = {"/edit/{id}"}, method = RequestMethod.POST)
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

    @RequestMapping(value = {"/addSubject"}, method = RequestMethod.GET)
    public ModelAndView addSubjectPage() {
        ModelAndView model = new ModelAndView();

        List<Teacher> teachers =teacherRepository.findAll();
        Map<String, Object> allObjectTeacher = new HashMap<String, Object>();
        allObjectTeacher.put("allTeachers", teachers);
        model.addAllObjects(allObjectTeacher);

        model.setViewName("admin-page/subject-service/add-subject");
        return model;
    }

    @RequestMapping(value = {"/addSubject"}, method = RequestMethod.POST)
    public ModelAndView addingSubject(@RequestParam("subjectName") String name,
                                      @RequestParam("year") String yearName,
                                      @RequestParam("teacher") String idTeacherStr ) {
        ModelAndView model = new ModelAndView("admin-page/subject-service/add-subject");
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

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.GET)
    public ModelAndView deleteSubjectPage(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView();
        Subject subject=subjectRepository.findOne(id);
        model.addObject("id",id);
        model.addObject("subjectName",subject.getName());
        model.addObject("year", subject.getYear());
        model.addObject("teacher", subject.getTeacher());
        model.setViewName("admin-page/subject-service/delete-subject");
        return model;
    }

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.POST)
    public String deleteSubject(@PathVariable Integer id){
        Subject subject=subjectRepository.findOne(id);
        markRepository.removeBySubject(subject);
        subjectRepository.removeById(id);
        return "redirect:/admin/subjectsList";
    }
}
