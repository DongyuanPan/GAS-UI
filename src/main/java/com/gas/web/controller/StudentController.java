package com.gas.web.controller;

import com.gas.web.bean.Res;
import com.gas.web.entity.Student;
import com.gas.web.service.IStudentService;
import com.gas.web.vo.Response;
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
     * 通过 id 查询学生
     * @return
     */
    @GetMapping("/{id}")
    public Response studentFindById(@PathVariable("id") Integer id) {
        Student student = null;
        try {
            student = studentService.studentFindById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("查询失败", id);
        }
        return Response.success("查询成功", student);
    }

    /**
     * 新增一个学生
     * @return
     */
    @PostMapping("/add")
    public Response studentAdd(@RequestParam("studentNum") String studentNum, @RequestParam("name") String name,
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
        try {
            studentService.studentAdd(student);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("添加失败", student);
        }
        return Response.success("添加成功", student);
    }


    /**
     * 通过 id 更新一个学生信息
     * @return
     */
    @PostMapping("/update/{id}")
    public Response studentUpdate(@PathVariable("id") Integer id,
                                  @RequestParam("studentNum") String studentNum, @RequestParam("name") String name,
                                  @RequestParam("sex") String sex, @RequestParam("enrollmentTime") String enrollmentTime,
                                  @RequestParam("phone") String phone, @RequestParam("email") String email,
                                  @RequestParam("degree") String degree, @RequestParam("type") String type,
                                  @RequestParam("employment") String employment) {
        Student student = new Student();
        student.setId(id);
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
        try {
            studentService.studentUpdate(student);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("编辑失败", student);
        }
        return Response.success("编辑成功", student);
    }

    /**
     * 通过 id 删除一个学生
     *
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public Response studentDelete(@PathVariable("id") Integer id) {
        try {
            studentService.studentDelete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", id);
        }
        return Response.success("删除成功", id);
    }


    @DeleteMapping("/delete")
    public Response studentDeleteBatch(@RequestBody List<Student> stuList) {
        try {
            studentService.studentDeleteBatch(stuList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", stuList);
        }
        return Response.success("删除成功", stuList);
    }

}
