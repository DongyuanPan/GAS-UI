package com.gas.web.controller;

import com.gas.web.entity.Project;

import com.gas.web.service.IProjectService;
import com.gas.web.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final IProjectService projectService;

    @Autowired
    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * 查询所有项目
     *
     * @return
     */
    @GetMapping("")
    public Map<String, Object> projectFindAll() {
        return projectService.projectFindAll();
    }

    /**
     * 按题目查询项目

     */
    @GetMapping("/title")
    public  Map<String, Object> projectFindByTitle(@RequestParam("title") String title){
        return projectService.projectFindByTitle(title);
    }

    /**
     * 通过 id 查询项目
     * @return
     */
    @GetMapping("/{id}")
    public Response projectFindById(@PathVariable("id") Integer id) {
        Project project = null;
        try {
            project = projectService.projectFindById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("查询失败", id);
        }
        return Response.success("查询成功", project);
    }

    /**
     * 新增一个项目
     * @return
     */
    @PostMapping("/add")
    public Response projectAdd(@RequestParam("title") String title,
                               @RequestParam("pic") String pic,
                               @RequestParam("startDate") String startDate,
                               @RequestParam("endDate") String endDate,
                               @RequestParam("type") String type) {
        Project project = new Project();
        project.setTitle(title);
        project.setPic(pic);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setType(type);
        //保存和更新都用该方法
        try {
            projectService.projectAdd(project);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("添加失败", project);
        }
        return Response.success("添加成功", project);
    }


    /**
     * 通过 id 更新一个项目信息
     * @return
     */
    @PostMapping("/update/{id}")
    public Response projectUpdate(@PathVariable("id") Integer id,
                                  @RequestParam("title") String title,
                                  @RequestParam("pic") String pic,
                                  @RequestParam("startDate") String startDate,
                                  @RequestParam("endDate") String endDate,
                                  @RequestParam("type") String type
                                  ) {
        Project project = new Project();
        project.setId(id);
        project.setTitle(title);
        project.setPic(pic);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setType(type);
        //保存和更新都用该方法
        try {
            projectService.projectUpdate(project);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("编辑失败", project);
        }
        return Response.success("编辑成功", project);
    }

    /**
     * 通过 id 删除一个项目
     *
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public Response projectDelete(@PathVariable("id") Integer id) {
        try {
            projectService.projectDelete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", id);
        }
        return Response.success("删除成功", id);
    }


    @DeleteMapping("/delete")
    public Response studentDeleteBatch(@RequestBody List<Project> projectList) {
        try {
            projectService.projectDeleteBatch(projectList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", projectList);
        }
        return Response.success("删除成功", projectList);
    }

}
