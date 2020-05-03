package com.gas.web.controller;

import com.gas.web.entity.Student;
import com.gas.web.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final IStudentService studentService;

    @Autowired
    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 查询所有学生列表
     *
     * @return
     */
    @GetMapping("")
    public Map<String, Object> studentFindAll() {
        return studentService.studentFindAll();
    }

    /**
     * 通过年龄查询学生
     *
     * @param enrollmentTime
     * @return
     */
    @GetMapping("/{age}")
    public List<Student> studentFindByAge(@PathVariable("age") String enrollmentTime) {
        return studentService.studentFindByAge(enrollmentTime);
    }

    /**
     * 新增一个学生
     * @return
     */
    @PostMapping("/add")
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
        return studentService.studentAdd(student);

    }


    /**
     * 通过ID更新一个学生信息
     * @return
     */
    @PutMapping("/update/{id}")
    public Student studentUpdate(@PathVariable("id") Integer id, @RequestParam("name") String name, @RequestParam("age") Integer age,
                                 @RequestParam("sex") String sex, @RequestParam("email") String email) {
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setSex(sex);
        student.setEmail(email);
        //保存和更新都用该方法
        return studentService.studentUpdate(student);
    }

    /**
     * 通过ID删除一个学生
     *
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public void studentDelete(@PathVariable("id") Integer id) {
        studentService.studentDelete(id);
    }


    @PostMapping("/delete")
    public void studentDeleteBatch(@RequestParam List<Integer> stuList) {
        studentService.studentDeleteBatch(stuList);
    }

}
