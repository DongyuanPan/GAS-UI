package com.gas.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.gas.web.entity.Algorithm;
import com.gas.web.service.IAlgorithmService;
import com.gas.web.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {

    private final IAlgorithmService algorithmService;

    private String lastFileName = null;

    private boolean isupload=false;

    private Integer searchid=null;

    public String getLastFileName() {
        return lastFileName;
    }

    public void setLastFileName(String lastFileName) {
        this.lastFileName = lastFileName;
    }

    @Autowired
    public AlgorithmController(IAlgorithmService algorithmService){ this.algorithmService = algorithmService; }

    /**
     * 查询所有算法
     *
     * @return
     */
    @GetMapping("")
    public Map<String, Object> algorithmFindAll() { return algorithmService.algorithmFindAll(); }

    /**
     * 按名称查询算法

     */
    @GetMapping("/name")
    public  Map<String, Object> algorithmFindAllByName(@RequestParam("name") String name){
         return algorithmService.findByNameLike(name);
    }


    /**
     * 通过 id 查询算法
     * @return
     */
    @GetMapping("/{id}")
    public Response algorithmFindById(@PathVariable("id") Integer id) {
        Algorithm algorithm = null;
        try {
            algorithm = algorithmService.algorithmFindById(id);
            searchid=id;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("查询失败", id);
        }
        return Response.success("查询成功", algorithm);
    }

    /**
     * 新增一个算法
     * @return
     */
    @PostMapping("/add")
    public Response algorithmAdd( @RequestParam("name") String name,
                                @RequestParam("summary") String summary) {
        Algorithm algorithm = new Algorithm();
        //等待文件上传
        while (true) {
            System.out.println("no algorithm file");
            File daxFile = new File("src/main/resources/AlgorithmFile/" + getLastFileName());
            if (daxFile.exists()&&daxFile.isFile())
            {
                algorithm.setPath(getLastFileName());
                break;
            }
        }
        algorithm.setName(name);
        algorithm.setSummary(summary);
        algorithm.setPath(getLastFileName());
        setLastFileName(null);
        //保存和更新都用该方法
        try {
            algorithmService.algorithmAdd(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("添加失败", algorithm);
        }
        return Response.success("添加成功", algorithm);
    }


    /**
     * 通过 id 更新一项算法
     * @return
     */
    @RequestMapping(value="/download/{id}", produces = { "application/json" },method = RequestMethod.POST)
    @ResponseBody
    public void download(HttpServletResponse response) throws IOException {
        Algorithm algorithm = null;
        algorithm = algorithmService.algorithmFindById(searchid);
        File file = new File("src/main/resources/AlgorithmFile/"+ algorithm.getPath());
        //获取要下载的文件名
        String fileName = file.getAbsolutePath();
        response.setCharacterEncoding("utf-8");
        response.setHeader("Pragma", "No-Cache");
        response.setHeader("Cache-Control", "No-Cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("application/msexcel; charset=UTF-8");
        response.setHeader("Content-disposition","attachment; filename=" + URLEncoder.encode(algorithm.getName(), "GBK"));// 设定输出文件头
        ServletOutputStream out = null;
        FileInputStream in = null; // 读入文件
        in = new FileInputStream(fileName);
        out = response.getOutputStream();
        out.flush();
        int aRead = 0;
        byte[] buffer = new byte[1024];
        while ((aRead = in.read(buffer)) != -1 & in != null) {
            out.write(buffer, 0, aRead);
        }
        out.flush();
        in.close();
        out.close();
    }

    @PostMapping("/update/{id}")
    public Response algorithmUpdate(@PathVariable("id") Integer id,
                                    @RequestParam("name") String name,
                                    @RequestParam("summary") String summary) throws IOException {
        Algorithm algorithm = new Algorithm();
        if(isupload) {
            while (true) {
                File daxFile = new File("src/main/resources/AlgorithmFile/" + getLastFileName());
                if (daxFile.exists() && daxFile.isFile()) {
                    algorithm.setPath(getLastFileName());
                    break;
                }
            }
        }else{
            algorithm.setPath(algorithmService.algorithmFindById(id).getPath());
        }
        isupload=false;
        algorithm.setId(id);
        algorithm.setName(name);
        algorithm.setSummary(summary);
        //编辑记录时，上传新的文件，即删除旧的专利文件，防止文件过多
        File oldfile = new File("src/main/resources/AlgorithmFile/"+algorithmService.algorithmFindById(id).getPath());
        String oldpath = oldfile.getAbsolutePath();
        File absoldfile = new File(oldpath);
        if(absoldfile.exists()&&absoldfile.isFile())
            absoldfile.delete();
        //保存和更新都用该方法
        try {
            algorithmService.algorithmUpdate(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("编辑失败", algorithm);
        }
        return Response.success("编辑成功", algorithm);
    }

    /**
     * 通过 id 删除一项算法
     *
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public Response algorithmDelete(@PathVariable("id") Integer id) {
        try {
            algorithmService.algorithmDelete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", id);
        }
        return Response.success("删除成功", id);
    }


    @DeleteMapping("/delete")
    public Response algorithmDeleteBatch(@RequestBody List<Algorithm> algorithmList) {
        try {
            algorithmService.algorithmDeleteBatch(algorithmList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", algorithmList);
        }
        return Response.success("删除成功", algorithmList);
    }

    @RequestMapping(value ="/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadFile(@RequestParam("file") MultipartFile file) {
        isupload=true;
        JSONObject jsonObject = new JSONObject();
        String dirStr = "src/main/resources/AlgorithmFile";
        if (file.isEmpty()) {
            isupload = true;
            jsonObject.put("code", -1);
            return jsonObject;
        }
        String fileName = file.getOriginalFilename();
        File fileDir = new File(dirStr);
        String path = fileDir.getAbsolutePath();
        if(!fileDir.exists()){
            fileDir.mkdir();
        }
        File algorithmfile = new File("src/main/resources/AlgorithmFile/"+fileName);
        if(algorithmfile.exists() && algorithmfile.isFile()) {
            System.out.println("file has existed");
            algorithmfile.delete();
        }
        try {
            file.transferTo(new File(path, fileName));
            setLastFileName(fileName);
            jsonObject.put("code", 200);
        } catch (Exception e) {
            jsonObject.put("code", 0);
            e.printStackTrace();
        }
        return jsonObject;

    }
}
