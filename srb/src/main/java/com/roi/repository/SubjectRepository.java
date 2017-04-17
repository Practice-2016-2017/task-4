package com.roi.repository;
import com.roi.entity.Subject;
import com.roi.entity.Teacher;
import com.roi.entity.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer>{
    List<Subject> findByTeacher(Teacher teacher);
    Subject findByNameAndYear (String name, Year year);

}
