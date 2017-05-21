package com.roi.repository;
import com.roi.entity.Subject;
import com.roi.entity.Teacher;
import com.roi.entity.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer>{
    List<Subject> findByTeacher(Teacher teacher);
    Subject findByNameAndYear (String name, Year year);
    boolean findByTeacher_Login(Integer id);
    @Transactional
    void removeById(Integer id);
}
