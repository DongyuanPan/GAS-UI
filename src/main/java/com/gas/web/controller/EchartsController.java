package com.gas.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.gas.web.bean.Res;
import com.gas.web.bean.Schedule;
import com.gas.web.bean.Task;
import com.gas.web.display.Display;
import com.google.gson.Gson;
import org.cloudbus.cloudsim.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.workflowsim.CondorVM;
import org.workflowsim.Job;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Controller
public class EchartsController {

    @ResponseBody
    @RequestMapping("/init")
    public HashMap<String, Object> init() {
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");
        res.put("data", genJson());
        return res;
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/echarts")
    public String echarts() {
        return "echarts";
    }

    @ResponseBody
    @RequestMapping("/FCFSExample")
    public HashMap<String, Object> FCFSExample() {
        String[] args = new String[]{};
        HashMap<String, Object> res = new HashMap<>();
        List<CondorVM> vmList = new ArrayList<>();
        List<Job> jobList = new ArrayList<>();
        SchedulingAlgorithm f = new SchedulingAlgorithm();
//        f.FCFS();

        res.put("code", "200");
        res.put("data", toDisplay(f.getCondorVMList(), f.getTaskList()));
        return res;
   }

    private Display toDisplay(List<CondorVM> vmList, List<Job> jobList) {
        Schedule schedule = new Schedule();
        List<Res> resList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        HashMap<Integer, Integer> VMidToIndex = new HashMap<>();
        HashMap<Integer, Integer> TaskidToIndex = new HashMap<>();

        int index = 0;
        for (Job job : jobList) {
            for (org.workflowsim.Task taskTmp : job.getTaskList()) {
                TaskidToIndex.put(taskTmp.getCloudletId(), index);
                ++index;
            }
        }

        for (int i = 0; i < vmList.size(); ++i) {
            CondorVM vm = vmList.get(i);
            Res res = new Res();
            res.setName(String.valueOf(vm.getId()));
            res.setType(String.valueOf(vm.getHost()));
            resList.add(res);
            VMidToIndex.put(vm.getId(), i);
        }

        for (Job job : jobList) {
            for (org.workflowsim.Task taskTmp : job.getTaskList()) {
                Task task = new Task();
                task.setIndexRes(VMidToIndex.get(job.getVmId()));
                task.setStartTime(taskTmp.getExecStartTime());
                task.setEndTime(taskTmp.getTaskFinishTime());
                task.setId(taskTmp.getCloudletId());
                task.setName(taskTmp.getType());
                task.setColorNum(new Random().nextInt(11));
                // 读取后继任务和前驱任务的id
                List<Integer> sucList = task.getSucList();
                List<Integer> preList = task.getPreList();
                task.setHightlight(false);
                for (org.workflowsim.Task taskChild : taskTmp.getChildList()) {
                    sucList.add(TaskidToIndex.get(taskChild.getCloudletId()));
                }
                for (org.workflowsim.Task taskParent : taskTmp.getParentList()) {
                    preList.add(TaskidToIndex.get(taskParent.getCloudletId()));
                }
                taskList.add(task);
            }
        }
        schedule.setResList(resList);
        schedule.setTaskList(taskList);
        return new Display(schedule);
    }

    private Display genJson() {
        Schedule schedule = new Schedule();
        List<Res> resList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        resList.add(new Res("AB95", "W"));
        resList.add(new Res("AB97", "W"));
        resList.add(new Res("AB98", "W"));
        taskList.add(new Task(0, 14, 149, "Y1713",false,1));
        taskList.add(new Task(0, 154, 249, "Y3803",false,2));
        taskList.add(new Task(1, 14, 149, "Y3901",false,3));
        taskList.add(new Task(1, 154, 249, "Y4654",false,4));
        taskList.add(new Task(2, 14, 149, "Y8626",false,5));
        taskList.add(new Task(2, 149, 296, "Y0050",false,6));
        schedule.setResList(resList);
        schedule.setTaskList(taskList);

        Gson gson = new Gson();
        Display display = new Display(schedule);
        String json = gson.toJson(display);
        System.out.println(json);
        return display;
//        System.out.println(json);

//        String jsonPath = "F:\\workspace\\IdeaProjects\\springboot_web\\src\\main\\resources\\static\\json";
//        CreateFileUtil.createJsonFile(json, jsonPath, "data");
    }

    private boolean finishUpload = false;
    private String lastFileName = null;

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
     * 文件上传
     * @param file 接收前端的formdata
     * @return 返回响应结果
     */
    @RequestMapping(value = "/uploadJsonFile", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject singleFileUpload(@RequestParam("file") MultipartFile file){
        JSONObject jsonObject = new JSONObject();
        if (file.isEmpty()) {
            jsonObject.put("code", -1);
            return jsonObject;
        }
        String fileName = file.getOriginalFilename();
        StringBuilder stringBuilder = new StringBuilder();
        File fileDir = new File("src/main/resources/static/upload");
        String path = fileDir.getAbsolutePath();
        if(!fileDir.exists()){
            fileDir.mkdir();
        }
        try {
            jsonObject.put("code", 200);
            JSONObject jsonobject = JSONObject.parseObject(new String(file.getBytes()));
            //jsonObject.put("data", new String(file.getBytes(),"UTF-8"));
            jsonObject.put("data", jsonobject);
            jsonObject.put("url", stringBuilder.append(path).append(fileName).toString());
//            file.transferTo(new File(path, fileName));
        } catch (Exception e) {
            jsonObject.put("code", 0);
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 任务文件上传
     * @param file 接收前端的formdata
     * @return 返回响应结果
     */
    @RequestMapping(value = "/taskFileUpload", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject taskFileUpload(@RequestParam("file")MultipartFile file){
        JSONObject jsonObject = new JSONObject();
        if (file.isEmpty()) {
            jsonObject.put("code", -1);
            return jsonObject;
        }
        String fileName = file.getOriginalFilename();
        setLastFileName(fileName);
        File fileDir = new File("src/main/resources/static/tasksim");
        String path = fileDir.getAbsolutePath();
        if(!fileDir.exists()){
            fileDir.mkdir();
        }
        try {
            file.transferTo(new File(path, fileName));
            setLastFileName(fileName);
            setFinishUpload(true);
            jsonObject.put("code", 200);
        } catch (Exception e) {
            jsonObject.put("code", 0);
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 表单数据上传
     * @return 返回响应结果
     */
    @RequestMapping(value = "/formUpload", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addQuestionnaire(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        String vmnum = request.getParameter("vmnum");
        String path = "src/main/resources/static/tasksim/";
        String algorithm = request.getParameter("algorithm");;
        boolean whetherSave = Boolean.parseBoolean(request.getParameter("switch"));

        while (true){
            System.out.println("sleep");
            File daxFile = new File(path + getLastFileName());
            if (daxFile.exists())
                break;
        }

//        while (getLastFileName() == null) {
//            System.out.println("sleep");
//        }

//        while (!finishUpload) {
//            System.out.println("sleep");
//        }

        SchedulingAlgorithm f = new SchedulingAlgorithm();
        f.process(path + getLastFileName(), Integer.parseInt(vmnum), Integer.parseInt(algorithm));

        jsonObject.put("code", 200);
        jsonObject.put("data", toDisplay(f.getCondorVMList(), f.getTaskList()));
        setFinishUpload(false);
        return jsonObject;
    }

}
