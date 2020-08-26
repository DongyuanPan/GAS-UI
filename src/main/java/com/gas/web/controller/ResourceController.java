package com.gas.web.controller;

import com.gas.web.entity.Resource;
import com.gas.web.service.IResourceService;
import com.gas.web.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("resource")
public class ResourceController {
    private final IResourceService resourceService;

    @Autowired
    public ResourceController(IResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * 查询所有资源
     *
     * @return
     */
    @GetMapping("")
    public Map<String, Object> resourceFindAll() { return resourceService.resourceFindAll(); }

    /**
     * 按虚拟机名称查找
     * @param name
     * @return
     */
    @GetMapping("/vmname")
    public  Map<String, Object> resourceFindByName(@RequestParam("name") String name){
        return  resourceService.findByNameLike(name);
    }

    /**
     * 通过 id 查询资源
     * @return
     */
    @GetMapping("/{id}")
    public Response resourceFindById(@PathVariable("id") Integer id) {
        Resource resource = null;
        try {
            resource = resourceService.resourceFindById(id);
            //searchid=id;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("查询失败", id);
        }
        return Response.success("查询成功", resource);
    }

    /**
     * 新增VM
     * @return
     */
    @PostMapping("/add/{user}")
    public Response patentAdd( @PathVariable("user") String user,
                               @RequestParam("vmname") String name, @RequestParam("count") Integer count,
                               @RequestParam("mirror") Integer mirror, @RequestParam("ram") Integer ram,
                               @RequestParam("mips") Integer mips, @RequestParam("bw") Integer bw,
                               @RequestParam("cpu") Integer cpu) {
        Resource resource=new Resource();
        resource.setUser(user);
        resource.setVmname(name);
        resource.setCount(count);
        resource.setMirror(mirror);
        resource.setRam(ram);
        resource.setMips(mips);
        resource.setBw(bw);
        resource.setCpu(cpu);
        resource.setEnrollmentTime(geTime());
        //保存和更新都用该方法
        try {
            resourceService.resourceAdd(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("添加失败", resource);
        }
        return Response.success("添加成功", resource);
    }

    /**
     *
     * 更新虚拟机
     * @return
     * @throws IOException
     */
    @PostMapping("/update/{id}")
    public Response patentUpdate(@PathVariable("id") Integer id,
                                 @RequestParam("vmname") String name, @RequestParam("count") Integer count,
                                 @RequestParam("mirror") Integer mirror, @RequestParam("ram") Integer ram,
                                 @RequestParam("mips") Integer mips, @RequestParam("bw") Integer bw,
                                 @RequestParam("cpu") Integer cpu, @RequestParam("enrollmentTime") String enrollmentTime) throws IOException {
        Resource resource=new Resource();
        resource.setId(id);
        resource.setUser(resourceService.resourceFindById(id).getUser());
        resource.setVmname(name);
        resource.setCount(count);
        resource.setMirror(mirror);
        resource.setRam(ram);
        resource.setMips(mips);
        resource.setBw(bw);
        resource.setCpu(cpu);
        resource.setEnrollmentTime(resourceService.resourceFindById(id).getEnrollmentTime());
        //保存和更新都用该方法
        try {
            resourceService.resourceUpdate(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("编辑失败", resource);
        }
        return Response.success("编辑成功", resource);
    }

    /**
     * 通过 id 删除虚拟机
     *
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public Response resourceDelete(@PathVariable("id") Integer id) {
        try {
            resourceService.resourceDelete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", id);
        }
        return Response.success("删除成功", id);
    }

    /**
     * 批量删除虚拟机
     * @param resList
     * @return
     */

    @DeleteMapping("/delete")
    public Response resourceDeleteBatch(@RequestBody List<Resource> resList) {
        try {
            resourceService.resourceDeleteBatch(resList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", resList);
        }
        return Response.success("删除成功", resList);
    }

    public String geTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2006);
        cal.set(Calendar.WEEK_OF_YEAR, 1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return df.format(cal.getTime()).toString();
    }
}

