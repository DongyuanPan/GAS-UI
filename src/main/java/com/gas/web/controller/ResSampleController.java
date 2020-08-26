package com.gas.web.controller;

import com.gas.web.dao.ResSampleDao;
import com.gas.web.entity.ResSample;
import com.gas.web.service.IResSampleService;
import com.gas.web.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("resSample")
public class ResSampleController {
    private final IResSampleService resSampleService;
    private final ResSampleDao resSampleDao;

    @Autowired
    public ResSampleController(IResSampleService resSampleService, ResSampleDao resSampleDao) {
        this.resSampleService = resSampleService;
        this.resSampleDao = resSampleDao;
    }

    /**
     * 查出当前用户的虚拟机模板
     * @param
     * @return
     */
   /* @GetMapping("/{user}")
    public Map<String, Object> sampleFindByUser(@PathVariable("user") String user) {
        ResSample resSample=null;
        try {
            resSample=resSampleService.sampleFindById(resSampleDao.findByUser(user).get(0));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("查询失败", user);
        }
           return  Response.success("查询成功", resSample);
    }*/

    @GetMapping("/{user}")
    public Response patentFindById(@PathVariable("user") String user) {
        ResSample resSample=null;
        try {
            Map<String, Object> map=resSampleService.findByUser(user);
            resSample=(ResSample)map.get("data");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("查询失败", user);
        }
        return Response.success("查询成功", resSample);
    }

    /**
     *
     * 更新当前用户虚拟机模板
     * @return
     * @throws IOException
     */
    @PostMapping("/update/{user}")
    public Response patentUpdate(@PathVariable("user") String user,
                                 @RequestParam("vmname") String name, @RequestParam("count") Integer count,
                                 @RequestParam("mirror") Integer mirror, @RequestParam("ram") Integer ram,
                                 @RequestParam("mips") Integer mips, @RequestParam("bw") Integer bw,
                                 @RequestParam("cpu") Integer cpu) throws IOException {
        ResSample resSample=new ResSample();
        resSample.setId(resSampleDao.findByUser(user).get(0));
        resSample.setUser(user);
        resSample.setVmname(name);
        resSample.setCount(count);
        resSample.setMirror(mirror);
        resSample.setRam(ram);
        resSample.setMips(mips);
        resSample.setBw(bw);
        resSample.setCpu(cpu);
        //保存和更新都用该方法
        try {
            resSampleService.resSampleUpdate(resSample);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("编辑失败", resSample);
        }
        return Response.success("编辑成功", resSample);
    }

}

