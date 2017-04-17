package com.roi.service;

import com.roi.entity.*;
import com.roi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private MarkRepository markRepository;

    public List<Student> findAllStudents() {return studentRepository.findAll();}

    public List<Student> getYearStudents (Year year) {return studentRepository.findByYear(year);}
    public List<Mark> getStudentMarks(Student student) {return markRepository.findByStudent(student);}

    public List<Mark> getSubjectMarks(Subject subject) {return markRepository.findBySubject(subject);}

    public List<Subject> getTeacherSubjects (Teacher teacher) {return subjectRepository.findByTeacher(teacher);}

    public Student findByLoginStudent(String name) {
        Integer login=Integer.parseInt(name.replaceAll("st",""));
        return studentRepository.findByLogin(login);}

    public Teacher findByLoginTeacher(String name) {
        Integer login=Integer.parseInt(name.replaceAll("te",""));
        return teacherRepository.findByLogin(login);}


}
