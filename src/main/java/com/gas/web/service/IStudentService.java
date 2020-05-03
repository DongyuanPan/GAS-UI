package com.gas.web.service;

import com.gas.web.entity.Student;

import java.util.List;
import java.util.Map;

public interface IStudentService {
    Map<String, Object> studentFindAll();

    List<Student> studentFindByAge(String enrollmentTime);

    Student studentAdd(Student student);

    Student studentUpdate(Student student);

    void studentDelete(Integer id);

    void studentDeleteBatch(List<Integer> stuList);
}
