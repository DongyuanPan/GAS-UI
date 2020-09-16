package com.gas.web.controller;

import com.gas.web.dao.ResSampleDao;
import com.gas.web.entity.ResSample;
import com.gas.web.entity.Resource;
import com.gas.web.service.IResSampleService;
import com.gas.web.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
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

    @RequestMapping("")
    public Map<String, Object> findAllRes() {

        return resSampleService.findAllSample();
    }
    /**
     * 查出当前用户的虚拟机模板
     * @param
     * @return
     */

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
    /**添加模板
     *
     *
     *
    */

    @PostMapping("/add")
    public Response addResource(ResSample resSample){

        try {
            resSampleDao.save(resSample);
        }catch (Exception e) {
            e.printStackTrace();
            return Response.failure("添加失败", resSample);
        }
        return Response.success("添加成功", resSample);
    }

    /** 批量删除模板
     *
     */
    @RequestMapping("/delete")
    public Response resourceDeleteBatch(@RequestBody List<ResSample> sampleList) {
        try {
            resSampleService.deleteBatch(sampleList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", sampleList);
        }
        return Response.success("删除成功", sampleList);
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

