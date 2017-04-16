package com.roi.repository;
import com.roi.entity.Mark;
import com.roi.entity.Student;
import com.roi.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Integer> {
    List<Mark> findByStudent(Student student);

    List<Mark> findBySubject (Subject subject);
}
