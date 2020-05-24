package com.gas.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.gas.web.entity.Patent;
import com.gas.web.service.IPatentService;
import com.gas.web.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/patent")
public class PatentController {

    private final IPatentService patentService;

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
    public PatentController(IPatentService patentService){ this.patentService = patentService; }

    /**
     * 查询所有专利
     *
     * @return
     */
    @GetMapping("")
    public Map<String, Object> patentFindAll() { return patentService.patentFindAll(); }

    /**
     * 按其他作者查询专利

     */
    @GetMapping("/name")
    public  Map<String, Object> patentFindAllByName(@RequestParam("name") String name){
         return patentService.findByNameLike(name);
    }

    /**
     * 按专利名称关键字查询专利
     * @param keyword
     * @return
     */
    @GetMapping("/keyword")
    public  Map<String, Object> patentFindAllByKey(@RequestParam("keyword") String keyword){
        return  patentService.findByPatnameLike(keyword);
    }

    /**
     * 按其他作者与专利名称关键字两个条件查询专利
     * @param name
     * @param keyword
     * @return
     */
    @GetMapping("/condition")
    public  Map<String, Object> patentFindAllByCondition(@RequestParam("name") String name,@RequestParam("keyword") String keyword){
        return patentService.findByDynamicCases(name,keyword);
    }

    /**
     * 通过 id 查询专利
     * @return
     */
    @GetMapping("/{id}")
    public Response patentFindById(@PathVariable("id") Integer id) {
        Patent patent = null;
        try {
            patent = patentService.patentFindById(id);
            searchid=id;
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
    public Response patentAdd( @RequestParam("name") String name, @RequestParam("secondname") String secondname,
                               @RequestParam("enrollmentTime") String enrollmentTime, @RequestParam("type") String type,
                               @RequestParam("patname") String patname, @RequestParam("summary") String summary) {
        Patent patent = new Patent();
        //等待文件上传
        while (true) {
            System.out.println("no patent file");
            File daxFile = new File("src/main/resources/PatentFile/" + getLastFileName());
            if (daxFile.exists()&&daxFile.isFile())
            {
                patent.setPath(getLastFileName());
                break;
            }
        }
        patent.setName(name);
        patent.setSecondname(secondname);
        patent.setEnrollmentTime(enrollmentTime);
        patent.setPatname(patname);
        patent.setType(type);
        patent.setSummary(summary);
        patent.setPath(getLastFileName());
        setLastFileName(null);
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
    @RequestMapping(value="/download/{id}", produces = { "application/json" },method = RequestMethod.POST)
    @ResponseBody
    public void download(HttpServletResponse response) throws IOException {
        Patent patent=null;
        patent = patentService.patentFindById(searchid);
        File file=new File("src/main/resources/PatentFile/"+ patent.getPath());
        //获取要下载的文件名
        String fileName=file.getAbsolutePath();
        response.setCharacterEncoding("utf-8");
        response.setHeader("Pragma", "No-Cache");
        response.setHeader("Cache-Control", "No-Cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("application/msexcel; charset=UTF-8");
        response.setHeader("Content-disposition","attachment; filename=" + URLEncoder.encode(patent.getName(), "GBK"));// 设定输出文件头
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
    public Response patentUpdate(@PathVariable("id") Integer id,
                                 @RequestParam("name") String name, @RequestParam("secondname") String secondname,
                                 @RequestParam("enrollmentTime") String enrollmentTime, @RequestParam("type") String type,
                                 @RequestParam("patname") String patname, @RequestParam("summary") String summary) throws IOException {
        Patent patent = new Patent();
        if(isupload) {
            while (true) {
                File daxFile = new File("src/main/resources/PatentFile/" + getLastFileName());
                if (daxFile.exists() && daxFile.isFile()) {
                    patent.setPath(getLastFileName());
                    break;
                }
            }
        }else{
            patent.setPath(patentService.patentFindById(id).getPath());
        }
        isupload=false;
        patent.setId(id);
        patent.setName(name);
        patent.setSecondname(secondname);
        patent.setEnrollmentTime(enrollmentTime);
        patent.setPatname(patname);
        patent.setType(type);
        patent.setSummary(summary);
        //编辑记录时，上传新的文件，即删除旧的专利文件，防止文件过多
        File oldfile=new File("src/main/resources/PatentFile/"+patentService.patentFindById(id).getPath());
        String oldpath=oldfile.getAbsolutePath();
        File absoldfile=new File(oldpath);
        if(absoldfile.exists()&&absoldfile.isFile())
            absoldfile.delete();
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

    @RequestMapping(value ="/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadFile(@RequestParam("file") MultipartFile file) {
        isupload=true;
        JSONObject jsonObject = new JSONObject();
        String dirStr = "src/main/resources/PatentFile";
        if (file.isEmpty()) {
            isupload=true;
            jsonObject.put("code", -1);
            return jsonObject;
        }
        String fileName = file.getOriginalFilename();
        File fileDir = new File(dirStr);
        String path = fileDir.getAbsolutePath();
        if(!fileDir.exists()){
            fileDir.mkdir();
        }
        File patentfile=new File("src/main/resources/PatentFile/"+fileName);
        if(patentfile.exists() && patentfile.isFile()) {
            System.out.println("file has existed");
            patentfile.delete();
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
