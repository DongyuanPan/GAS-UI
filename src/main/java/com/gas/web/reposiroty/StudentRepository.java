package com.gas.web.reposiroty;

import com.gas.web.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer> {

    List<Student> findByEnrollmentTime(String enrollmentTime);

}
