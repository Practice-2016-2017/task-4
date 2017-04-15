package com.roi.service;

import com.roi.entity.Admin;
import com.roi.entity.Mark;
import com.roi.entity.Student;
import com.roi.entity.Teacher;
import com.roi.repository.AdminRepository;
import com.roi.repository.MarkRepository;
import com.roi.repository.StudentRepository;
import com.roi.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private MarkRepository markRepository;

    public List<Student> findAll () {return studentRepository.findAll();}

    public List<Mark> getMarks (Student student) {return markRepository.findByStudent(student);}

    public Student findByNameStudent(String name) {
        Integer login=Integer.parseInt(name.replaceAll("st",""));
        return studentRepository.findByLogin(login);}

    public Teacher findByNameTeacher(String name) {
        Integer login=Integer.parseInt(name.replaceAll("te",""));
        return teacherRepository.findByLogin(login);}

}
