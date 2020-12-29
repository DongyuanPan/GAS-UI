package com.gas.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gas.web.StaticAlgorithm.GAMain;
import com.gas.web.bean.Res;
import com.gas.web.bean.Schedule;
import com.gas.web.bean.Task;
import com.gas.web.constant.Constant;
import com.gas.web.display.Display;
import com.gas.web.entity.Vm;
import com.gas.web.entity.Workflow;
import com.gas.web.service.IAlgorithmService;
import com.gas.web.service.IVmService;
import com.gas.web.service.IWorkflowService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.workflowsim.CondorVM;
import org.workflowsim.Job;
import org.workflowsim.WorkflowScheduler;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Controller
public class EchartsController {
    int datacenter=1,vmnumber=0;
    boolean nonefile=false;
    public Map<String, Vm> map;
    private final IVmService iVmService;
    private final IWorkflowService iWorkflowService;
    private double finishtime=0;

    @Autowired
    public EchartsController(IVmService iVmService, IWorkflowService iWorkflowService) {
        this.iVmService = iVmService;
        this.iWorkflowService = iWorkflowService;
    }

    @ResponseBody
    @RequestMapping("/init")
    public HashMap<String, Object> init() {
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", "200");
        res.put("data", genJson());
        List<Class<?>> classList =  WorkflowScheduler.getClasses(Constant.algorithmPackageName);
        List<String> algoNameList = new ArrayList<>();
        for (Class<?> aClass : classList) {
            algoNameList.add(aClass.getSimpleName());
        }
        res.put("algoNameList", algoNameList);
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

    public Display toDisplay(List<CondorVM> vmList, List<Job> jobList) {
        finishtime=0;
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
                finishtime=Math.max(taskTmp.getTaskFinishTime(),finishtime);
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
    public void getResource(String name){
        //map=new HashMap<String, Object>();
        try {
            map=iVmService.getAllVm(Integer.parseInt(name));
            for (Vm vm:map.values()) {
                vmnumber+=vm.getCount();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
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
     * Json文件上传
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
            jsonObject.put("data", jsonobject);
            jsonObject.put("url", stringBuilder.append(path).append(fileName).toString());
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
        nonefile=file.isEmpty();
        if (file.isEmpty()) {
            jsonObject.put("code", -1);
            return jsonObject;
        }
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        File taskfile=new File("src/main/resources/static/tasksim"+fileName);
        if(taskfile.exists() && taskfile.isFile()) {
            System.out.println("file has existed");
            taskfile.delete();
        }
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
    public JSONObject addQuestionnaire(HttpServletRequest request) throws IOException {
        JSONObject jsonObject = new JSONObject();
        File fileDir = new File("config/workflow/");
        if(!fileDir.exists()){
            fileDir.mkdir();
        }
        String absolutePath = fileDir.getAbsolutePath();
        String workflowId = request.getParameter("workflow");
        String resourseId = request.getParameter("resource");
        String algorithm = request.getParameter("algorithm");
        JSONArray algorithmArray=JSONArray.parseArray(algorithm);
        algorithm=algorithmArray.getJSONObject(0).get("name").toString();
        String algorithm2 = null;
        Workflow workflow = iWorkflowService.workflowFindById(Integer.parseInt(workflowId));
        lastFileName = workflow.getFileName();
        getResource(resourseId);
        SchedulingAlgorithm f = new SchedulingAlgorithm();
        f.process(absolutePath + "/" + getLastFileName(), vmnumber, algorithm, map, datacenter);
        jsonObject.put("code", 200);
        jsonObject.put("algorithm1", algorithm);
        jsonObject.put("data", toDisplay(f.getCondorVMList(), f.getTaskList()));
        jsonObject.put("finishtime1", finishtime);
        if (algorithmArray.size() == 2) {
            algorithm2 = algorithmArray.getJSONObject(1).get("name").toString();
            //algorithmName2 = iAlgorithmService.algorithmFindById(Integer.parseInt(algorithm2));
            f.process(absolutePath + "/" + getLastFileName(), vmnumber, algorithm2, map, datacenter);
            jsonObject.put("algorithm2", algorithm2);
            jsonObject.put("data2", toDisplay(f.getCondorVMList(), f.getTaskList()));
            jsonObject.put("finishtime2", finishtime);
        }
        System.out.println(jsonObject);
        lastFileName = null;
        vmnumber = 0;
        return jsonObject;
    }
    /**
     * test ajax
     */
    @RequestMapping(value="/getDynamic",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getData() throws IOException, InterruptedException {
        JSONObject jsonObject;
        StringBuffer s=new StringBuffer();
        FileInputStream in=new FileInputStream(new File("D:\\Project3\\GAS-UI\\src\\main\\resources\\GaGeneration\\EveryGeneration.json"));
       BufferedReader reader=new BufferedReader(new InputStreamReader(in,"utf-8"));
        String s1 = reader.readLine();
       while (s1!=null)
        {
            s.append(s1);
            s1=reader.readLine();
        }
       jsonObject= JSON.parseObject(s.toString());
       in.close();
        return jsonObject;
    }
}
