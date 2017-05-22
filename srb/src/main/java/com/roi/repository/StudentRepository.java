package com.roi.repository;
import com.roi.entity.Student;
import com.roi.entity.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByLogin(Integer login);
    @Transactional
    void removeById(Integer id);
    List<Student> findByYear (Year year);
}
