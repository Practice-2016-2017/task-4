package com.roi.repository;
import com.roi.entity.Student;
import com.roi.entity.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByLogin(Integer login);
    Student findByName(String name);
    List<Student> findByYear (Year year);
}
