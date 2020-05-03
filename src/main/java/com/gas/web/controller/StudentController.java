package com.gas.web.controller;

import com.gas.web.entity.Student;
import com.gas.web.reposiroty.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rongrong
 * @version 1.0
 * @description:
 * @date 2019/12/30 20:40
 */
@RestController
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    /**
     * 查询所有学生列表
     *
     * @return
     */
    @GetMapping("/students")
    public Map<String, Object> studentFindAll() {
        //构造返回的 JSON 注意有格式要求   参见  resources/static/api/table.json
        Map<String, Object> res = new HashMap<>();
        List<Student> studentData = studentRepository.findAll();
        String resCode = "0";
        if (studentData == null)
            resCode = "1";
        res.put("code", resCode);
        res.put("msg", "");
        res.put("count", studentData.size());
        res.put("data", studentData);
        return res;
    }

    /**
     * 通过年龄查询学生
     *
     * @param enrollmentTime
     * @return
     */
    @GetMapping("/studentFindByAge/{age}")
    public List<Student> studentFindByAge(@PathVariable("age") String enrollmentTime) {
        return studentRepository.findByEnrollmentTime(enrollmentTime);
    }

    /**
     * 新增一个学生
     * @param studentNum
     * @param name
     * @param sex
     * @param enrollmentTime
     * @param phone
     * @param email
     * @param degree
     * @param type
     * @param employment
     * @return
     */
    @PostMapping("/studentAdd")
    public Student studentAdd(@RequestParam("studentNum") String studentNum, @RequestParam("name") String name,
                              @RequestParam("sex") String sex, @RequestParam("enrollmentTime") String enrollmentTime,
                              @RequestParam("phone") String phone, @RequestParam("email") String email,
                              @RequestParam("degree") String degree, @RequestParam("type") String type,
                              @RequestParam("employment") String employment) {
        Student student = new Student();
        student.setStudentNum(studentNum);
        student.setName(name);
        student.setSex(sex);
        student.setEnrollmentTime(enrollmentTime);
        student.setPhone(phone);
        student.setEmail(email);
        student.setDegree(degree);
        student.setType(type);
        student.setEmployment(employment);

        //保存和更新都用该方法
        return studentRepository.save(student);

    }


    /**
     * 通过ID更新一个学生信息
     *
     * @param id
     * @param name
     * @param age
     * @param sex
     * @param email
     * @return
     */
    @PutMapping("/studentUpdate/{id}")
    public Student studentUpdate(@PathVariable("id") Integer id, @RequestParam("name") String name, @RequestParam("age") Integer age,
                                @RequestParam("sex") String sex, @RequestParam("email") String email) {
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setSex(sex);
        student.setEmail(email);
        //保存和更新都用该方法
        return studentRepository.save(student);
    }

    /**
     * 通过ID删除一个学生
     *
     * @param id
     */
    @DeleteMapping("/studentDelete/{id}")
    public void studentDelete(@PathVariable("id") Integer id) {
        studentRepository.deleteById(id);
    }



}
