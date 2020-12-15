package com.gas.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.gas.web.entity.Paper;
import com.gas.web.entity.Workflow;
import com.gas.web.service.IPaperService;
import com.gas.web.service.IWorkflowService;
import com.gas.web.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {

    private final IWorkflowService workflowService;

    @Autowired
    public WorkflowController(IWorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    /**
     * 查询所有工作流列表
     *
     * @return
     */
    @GetMapping("")
    public Map<String, Object> workflowFindAll() {
        return workflowService.workflowFindAll();
    }

    /**
     * 通过 id 查询工作流
     * @return
     */
    @GetMapping("/{id}")
    public Response workflowFindById(@PathVariable("id") Integer id) {
        Workflow workflow = null;
        try {
            workflow = workflowService.workflowFindById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("查询失败", id);
        }
        return Response.success("查询成功", workflow);
    }


    /**
     * 通过 id 更新一个工作流信息
     * @return
     */
    @PostMapping("/update/{id}")
    public Response paperUpdate(@PathVariable("id") Integer id,
                                @RequestParam("title") String title, @RequestParam("information") String information
    ) {
        Workflow workflow = workflowService.workflowFindById(id);
        workflow.setInformation(information);
        workflow.setTitle(title);

        //保存和更新都用该方法
        try {
            workflowService.workflowUpdate(workflow);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("编辑失败", workflow);
        }
        return Response.success("编辑成功", workflow);
    }

    /**
     * 通过 id 删除一个工作流
     *
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public Response paperDelete(@PathVariable("id") Integer id) {
        try {
            Workflow workflow=workflowService.workflowFindById(id);
            File file=new File("config/workflow/"+workflow.getFileName());
            file.delete();
            workflowService.workflowDelete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", id);
        }
        return Response.success("删除成功", id);
    }


    @DeleteMapping("/delete")
    public Response studentDeleteBatch(@RequestBody List<Workflow> wfList) {
        try {
            workflowService.workflowDeleteBatch(wfList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", wfList);
        }
        return Response.success("删除成功", wfList);
    }


    private boolean finishUpload = false;
    private boolean isUpload = false;
    private String lastFileName = null;
    //工作流所保存的文件夹
    private String dirName = "config/workflow";

    public boolean isFinishUpload() {
        return finishUpload;
    }

    public void setFinishUpload(boolean finishUpload) {
        this.finishUpload = finishUpload;
    }

    public String getLastFileName() {
        return lastFileName;
    }

    public void setLastFileName(String lastFileName) {
        this.lastFileName = lastFileName;
    }



    /**
     * 任务文件上传
     * @param file 接收前端的formdata
     * @return 返回响应结果
     */
    @RequestMapping(value ="/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject xmlFileUpload(@RequestParam("file") MultipartFile file){
        isUpload=false;
        System.out.println("no");
        JSONObject jsonObject = new JSONObject();
        if (file.isEmpty()) {
            jsonObject.put("code", -1);
            isUpload=false;
            finishUpload=true;
            return jsonObject;
        }
        String fileName = file.getOriginalFilename();
        File taskfile=new File(dirName+"/"+fileName);
        if(taskfile.exists() && taskfile.isFile()) {
            System.out.println("file has existed");
            taskfile.delete();
        }
        File fileDir = new File(dirName);
        String path = fileDir.getAbsolutePath();
        if(!fileDir.exists()){
            fileDir.mkdir();
        }
        try {
            file.transferTo(new File(path, fileName));
            setLastFileName(fileName);
            setFinishUpload(true);
            jsonObject.put("code", 200);
            isUpload=true;
        } catch (Exception e) {
            jsonObject.put("code", 0);
            isUpload=false;
            e.printStackTrace();
        }
        finishUpload=true;
        return jsonObject;
    }
    /**
     * 新增一个论文
     * @return
     */
    @PostMapping("/add")
    public Response workflowAdd(@RequestParam("title") String title, @RequestParam("information") String information
    ) {
        Workflow workflow = new Workflow();
        while (true) {
            System.out.println("no file");

            File daxFile = new File("config/workflow/" + getLastFileName());
            if (daxFile.exists()&&daxFile.isFile())
            {
                break;
            }
            //System.out.println(daxFile.getAbsolutePath());
        }
        workflow.setTitle(title);
        workflow.setInformation(information);
        workflow.setFileName(getLastFileName());
        workflow.setLocalAddress(dirName+"/"+getLastFileName());
        setLastFileName(null);

        //保存和更新都用该方法
        try {
            workflowService.workflowAdd(workflow);
        } catch (Exception e) {
            e.printStackTrace();
            finishUpload=false;
            return Response.failure("添加失败", workflow);
        }
        finishUpload=false;
        return Response.success("添加成功", workflow);
    }

}
