package com.gas.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
     * @return 包含上传信息的json
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
     * 文件上传
     * @param file 接收前端的formdata
     * @return 包含上传信息的json
     */
    @RequestMapping(value = "/SimUpload", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject SimUpload(@RequestParam("file")MultipartFile file){
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
}
