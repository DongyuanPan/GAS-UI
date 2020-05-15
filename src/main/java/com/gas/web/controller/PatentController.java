package com.gas.web.controller;

import com.gas.web.entity.Patent;
import com.gas.web.service.IPatentService;
import com.gas.web.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patent")
public class PatentController {

    private final IPatentService patentService;

    @Autowired
    public PatentController(IPatentService patentService){ this.patentService = patentService; }

    /**
     * 查询所有专利
     *
     * @return
     */
    @GetMapping("")
    public Map<String, Object> patentFindAll() { return patentService.patentFindAll(); }

    /**
     * 通过 id 查询专利
     * @return
     */
    @GetMapping("/{id}")
    public Response patentFindById(@PathVariable("id") Integer id) {
        Patent patent = null;
        try {
            patent = patentService.patentFindById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("查询失败", id);
        }
        return Response.success("查询成功", patent);
    }

    /**
     * 新增一个专利
     * @return
     */
    @PostMapping("/add")
    public Response patentAdd(@RequestParam("studentNum") String studentNum, @RequestParam("name") String name,
                               @RequestParam("degree") String degree, @RequestParam("enrollmentTime") String enrollmentTime,
                               @RequestParam("patname") String patname,
                               @RequestParam("type") String type,@RequestParam("summary") String summary,
                               @RequestParam("email") String email) {
        Patent patent = new Patent();
        patent.setStudentNum(studentNum);
        patent.setName(name);
        patent.setDegree(degree);
        patent.setEnrollmentTime(enrollmentTime);
        patent.setPatname(patname);
        patent.setType(type);
        patent.setSummary(summary);
        patent.setEmail(email);
        //保存和更新都用该方法
        try {
            patentService.patentAdd(patent);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("添加失败", patent);
        }
        return Response.success("添加成功", patent);
    }


    /**
     * 通过 id 更新一项专利
     * @return
     */
    @PostMapping("/update/{id}")
    public Response patentUpdate(@PathVariable("id") Integer id,
                                 @RequestParam("studentNum") String studentNum, @RequestParam("name") String name,
                                  @RequestParam("degree") String degree,
                                  @RequestParam("enrollmentTime") String enrollmentTime,@RequestParam("patname") String patname,
                                  @RequestParam("type") String type,@RequestParam("summary") String summary,
                                  @RequestParam("email") String email) {
        Patent patent = new Patent();
        patent.setId(id);
        patent.setStudentNum(studentNum);
        patent.setName(name);
        patent.setDegree(degree);
        patent.setEnrollmentTime(enrollmentTime);
        patent.setPatname(patname);
        patent.setType(type);
        patent.setSummary(summary);
        patent.setEmail(email);
        //保存和更新都用该方法
        try {
            patentService.patentUpdate(patent);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("编辑失败", patent);
        }
        return Response.success("编辑成功", patent);
    }

    /**
     * 通过 id 删除一项专利
     *
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public Response patentDelete(@PathVariable("id") Integer id) {
        try {
            patentService.patentDelete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", id);
        }
        return Response.success("删除成功", id);
    }


    @DeleteMapping("/delete")
    public Response patentDeleteBatch(@RequestBody List<Patent> patList) {
        try {
            patentService.patentDeleteBatch(patList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", patList);
        }
        return Response.success("删除成功", patList);
    }
}
