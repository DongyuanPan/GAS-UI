package com.gas.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.gas.web.constant.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.workflowsim.WorkflowScheduler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadController {

    /**
     * 上传算法的 Java 文件
     * @param file
     * @return
     */
    @PostMapping(value = "/uploadAlgoFile")
    @ResponseBody
    public JSONObject uploadAlgoFile(@RequestParam("file") MultipartFile file) {
        JSONObject jsonObject = new JSONObject();

        String dirStr = "src/main/java/org/workflowsim/scheduling";

        if (file.isEmpty()) {
            jsonObject.put("code", -1);
            return jsonObject;
        }
        String fileName = file.getOriginalFilename();
        File fileDir = new File(dirStr);
        String path = fileDir.getAbsolutePath();
        if(!fileDir.exists()){
            fileDir.mkdir();
        }
        try {
            file.transferTo(new File(path, fileName)); // 保存到项目

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int result = compiler.run(null, null, null,
                    "-encoding", "UTF-8",
                    "-classpath", Constant.classPath,
                    "-d", Constant.classPath,
                    path + "/" + fileName);
            System.out.println(result==0?"编译成功":"编译失败");
//            URL[] urls = new URL[]{new URL("file:/"+"f:/")};
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            Class<?> c = loader.loadClass(Constant.algorithmPackageName + "." + fileName.replace(".java", ""));

            // 更新算法选择列表
            List<Class<?>> classList =  WorkflowScheduler.getClasses(Constant.algorithmPackageName);
            List<String> algoNameList = new ArrayList<>();
            for (Class<?> aClass : classList) {
                algoNameList.add(aClass.getSimpleName());
            }
            jsonObject.put("code", 200);
            jsonObject.put("data", algoNameList);
        } catch (Exception e) {
            jsonObject.put("code", -1);
            e.printStackTrace();
        }

        return jsonObject;
    }

}
