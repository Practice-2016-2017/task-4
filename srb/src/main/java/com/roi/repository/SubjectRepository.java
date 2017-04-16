package com.roi.repository;
import com.roi.entity.Subject;
import com.roi.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer>{
    List<Subject> findByTeacher(Teacher teacher);
    Subject findByName (String name);

}
