package com.gas.web.service;

import com.gas.web.entity.Student;

import java.util.List;
import java.util.Map;

public interface IStudentService {
    Map<String, Object> studentFindAll();

    Student studentFindById(Integer id);

    List<Student> studentFindByAge(String enrollmentTime);

    Student studentUpdate(Student student);

    Student studentAdd(Student student);

    void studentDelete(Integer id);

    void studentDeleteBatch(List<Student> stuList);
}
