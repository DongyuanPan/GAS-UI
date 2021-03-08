package com.gas.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.gas.web.entity.Paper;
import com.gas.web.entity.Workflow;
import com.gas.web.service.IPaperService;
import com.gas.web.service.IWorkflowService;
import com.gas.web.vo.Response;
import org.dom4j.io.SAXReader;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.*;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

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

    @GetMapping("/display/{id}")
    @ResponseBody
    public JSONObject displayWorkflow(@PathVariable("id") Integer id) throws IOException, JDOMException, DocumentException {
        JSONObject jsonObject = new JSONObject();
        Workflow workflow = null;
        workflow = workflowService.workflowFindById(id);
        String path = workflow.getLocalAddress();
        ArrayList<JSONObject> points = new ArrayList<JSONObject>();
        ArrayList<JSONObject> lines =new ArrayList<JSONObject>();


        //1.创建Reader对象
        SAXReader reader = new SAXReader();
        //2.加载xml
        Document document = reader.read(new File(path));
        //3.获取根节点
        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        while (iterator.hasNext()){
            Element stu = (Element) iterator.next();
            if(stu.getName()=="child") {
                List<Attribute> attributes = stu.attributes();
                String point = attributes.get(0).getValue();
 //               JSONObject jsonObjectPoint = new JSONObject();
 //               jsonObjectPoint.put("name",point);
 //               jsonObjectPoint.put("level",0);
 //               points.add(jsonObjectPoint);
                Iterator iterator1 = stu.elementIterator();
                while (iterator1.hasNext()) {
                    Element stuChild = (Element) iterator1.next();
                    List<Attribute> attributesChild = stuChild.attributes();
                    String spoint = attributesChild.get(0).getValue();
                    JSONObject jsonObjectLine = new JSONObject();
                    jsonObjectLine.put("source",spoint);
                    jsonObjectLine.put("target",point);
                    lines.add(jsonObjectLine);
                    int tt=0;
                    for(;tt<points.size(); tt++){
                        if(spoint.equals(points.get(tt).get("name").toString()))break;
                    }
                    if(tt==points.size()){
                        JSONObject jsonObjectSPoint = new JSONObject();
                        jsonObjectSPoint.put("name",spoint);
                        jsonObjectSPoint.put("level",0);
                        points.add(jsonObjectSPoint);
                    }
                    int pp=0;
                    for(;pp<points.size(); pp++){
                        if(point.equals(points.get(pp).get("name").toString()))break;
                    }
                    if(pp==points.size()){
                        JSONObject jsonObjectPoint = new JSONObject();
                        jsonObjectPoint.put("name",point);
                        jsonObjectPoint.put("level",0);
                        points.add(jsonObjectPoint);
                    }
                }

            }
        }




        int levels[] = new int[points.size()];
        int flag = 1;
        do {
            flag = 1;
            for (int i = 0; i < lines.size(); i++) {
                System.out.println("------------line-------");
                System.out.println(i);
                System.out.println("------------line-------");
                String source = lines.get(i).get("source").toString();
                String target = lines.get(i).get("target").toString();
                System.out.print("source");
                System.out.println(source);
                System.out.print("target");
                System.out.println(target);
                int temp_level = 0;
                for (int j = 0; j < points.size(); j++) {

                    if (points.get(j).get("name").toString().equals(source)) {
                        System.out.print(j);
                        System.out.println("OK");
                        temp_level = Integer.parseInt(points.get(j).get("level").toString());
                        System.out.print("source");
                        System.out.print(points.get(j).get("name"));
                        System.out.print(points.get(j).get("    "));
                        System.out.println(points.get(j).get("level"));
                        break;
                    }
                    if (j >= points.size()) {
                        System.out.println("fuck");
                    }
                }
                for (int j = 0; j < points.size(); j++) {
                    if (points.get(j).get("name").toString().equals(target)) {
                        System.out.print(j);
                        System.out.println("OK");
                        if (Integer.parseInt(points.get(j).get("level").toString()) < temp_level + 1) {
                            flag = 0;
                            points.get(j).put("level", temp_level + 1);
                            System.out.print("target");
                            System.out.print(points.get(j).get("name"));
                            System.out.print("    ");
                            System.out.println(points.get(j).get("level"));

                        }

                        break;

                    }
                    if (j >= points.size()) {
                        System.out.println("fuck");
                    }

                }

            }
        }while(flag==0);

        for(int j=0; j<points.size();j++){
            levels[Integer.parseInt(points.get(j).get("level").toString())]++;
        }
        int level_max=0, lever_num=0;

        for(int k=0;k<levels.length;k++){
            if(level_max<levels[k]){
                level_max = levels[k];
            }
            if(levels[k]>0){
                lever_num++;
            }
        }
        double y_distance=300;
        double y_start=100;
        double x_distance=300.0*level_max/lever_num*1.8;
        double x_start=300;
        double level_index[] = new double[lever_num];
        for(int l=0;l<lever_num;l++){
            level_index[l] = (level_max - levels[l])/2.0*y_distance+y_start;
        }
        for(int j=0; j<points.size();j++){

            points.get(j).put("x",Integer.parseInt(points.get(j).get("level").toString())*x_distance+x_start);
            points.get(j).put("y",level_index[Integer.parseInt(points.get(j).get("level").toString())]+y_distance);
//            points.get(j).remove("level");
            level_index[Integer.parseInt(points.get(j).get("level").toString())]=level_index[Integer.parseInt(points.get(j).get("level").toString())]+y_distance;
        }
        int cccc=0;




/*
        for(int j=0; j<points.size();j++){
            points.get(j).remove("level");
        }
*/










        JSONObject jsonObjectLineStyle = new JSONObject();
        jsonObjectLineStyle.put( "opacity", 0.9);
        jsonObjectLineStyle.put( "width", 5);
        jsonObjectLineStyle.put( "curveness", 0);
        jsonObject.put("data",points);
        jsonObject.put("links",lines);
        jsonObject.put("lineStyle",jsonObjectLineStyle);

        System.out.print(jsonObject);
        return jsonObject;
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
    private String  dirName = "config/workflow";

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
