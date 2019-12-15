package com.gas.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
public class UploadController {
    /**
     * 文件上传
     * @param file 接收前端的formdata
     * @return 返回响应结果
     */
    @RequestMapping(value = "/uploadJsonFile", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject singleFileUpload(@RequestParam("file")MultipartFile file){
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
        jsonObject.put("code", 200);
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
        jsonObject.put("code", 200);
        String title = request.getParameter("title");
        boolean whetherSave = Boolean.parseBoolean(request.getParameter("switch"));
        if (whetherSave) {
            // 保存结果
            /*File fileDir = new File("src/main/resources/static/save");
            String path = fileDir.getAbsolutePath();
            if(!fileDir.exists()){
                fileDir.mkdir();
            }
            FileOutputStream fop = null;
            File file;
            String content = "This is the text content";
            try {
                file = new File("c:/newfile.txt");
                fop = new FileOutputStream(file);
                // if file doesnt exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }
                // get the content in bytes
                byte[] contentInBytes = content.getBytes();
                fop.write(contentInBytes);
                fop.flush();
                fop.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fop != null) {
                        fop.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
        }
//        jsonObject.put("title", title);
//        jsonObject.put("whetherSave", whetherSave);
        return jsonObject;
    }

}
