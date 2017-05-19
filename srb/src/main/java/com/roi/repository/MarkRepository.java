package com.roi.repository;
import com.roi.entity.Mark;
import com.roi.entity.Student;
import com.roi.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Integer> {
    List<Mark> findByStudent(Student student);

    List<Mark> findBySubject (Subject subject);
    @Transactional
    void removeByStudent (Student student);

    @Transactional
    void  removeBySubject(Subject subject);
}
