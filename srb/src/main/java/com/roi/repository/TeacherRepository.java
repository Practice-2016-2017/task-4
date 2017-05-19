package com.roi.repository;
import com.roi.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TeacherRepository extends JpaRepository<Teacher,Integer> {
   Teacher findByLogin(Integer login);
   Teacher findById(Integer id);
   @Transactional
   void removeById(Integer id);
}
