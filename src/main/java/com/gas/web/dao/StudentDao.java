package com.gas.web.dao;

import com.gas.web.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface StudentDao extends JpaRepository<Student,Integer> {

    List<Student> findByEnrollmentTime(String enrollmentTime);

    @Modifying
    @Transactional
    @Query(value = "delete s from student s where s.id in (:idList)", nativeQuery = true)
    void deleteBatch(List<Integer> idList);
}
