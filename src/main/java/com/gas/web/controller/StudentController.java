package com.gas.web.controller;

import com.gas.web.entity.Student;
import com.gas.web.reposiroty.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Student> studentFindAll() {
        return studentRepository.findAll();
    }

    /**
     * 通过年龄查询学生
     *
     * @param age
     * @return
     */
    @GetMapping("/studentFindByAge/{age}")
    public List<Student> studentFindByAge(@PathVariable("age") Integer age) {
        return studentRepository.findByAge(age);
    }

    /**
     * 新增一个学生
     *
     * @param name
     * @param age
     * @param sex
     * @param email
     * @return
     */
    @PostMapping("/studentAdd")
    public Student studentAdd(@RequestParam("name") String name, @RequestParam("age") Integer age,
                             @RequestParam("sex") String sex, @RequestParam("email") String email) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setSex(sex);
        student.setEmail(email);
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
        student.setAge(age);
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
