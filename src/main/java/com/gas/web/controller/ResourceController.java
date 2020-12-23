package com.gas.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gas.web.entity.Host;
import com.gas.web.entity.Resource;
import com.gas.web.entity.Vm;
import com.gas.web.service.IHostService;
import com.gas.web.service.IResourceService;
import com.gas.web.service.IVmService;
import com.gas.web.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("resource")
public class ResourceController {
    private final IResourceService iResourceService;
    private final IVmService iVmService;
    private final IHostService iHostService;

    @Autowired
    public ResourceController(IResourceService iResourceService, IVmService iVmService, IHostService iHostService) {
        this.iResourceService = iResourceService;
        this.iVmService = iVmService;
        this.iHostService = iHostService;
    }

    @RequestMapping("")
    public Map<String, Object> findAllRes() {

        return iResourceService.resourceFindAll();
    }
    @RequestMapping("/getVms/{resId}")
    public Map<String,Object> getVmsForOneRes(@PathVariable("resId") Integer id){
        return iResourceService.vmFindAll(id);
    }

    //按资源名称搜索
    @GetMapping("/resname")
    public  Map<String, Object> resourceFindByName(@RequestParam("name") String name){
        return  iResourceService.findByNameLike(name);
    }
    @GetMapping("/{id}")
    public Map<String, Object> vmFindAllByRes(@PathVariable("id") Integer id) {
        return iResourceService.vmFindAll(id);
    }

    //添加资源
    @PostMapping("/add")
    public Response addResource(@RequestParam("name") String name,@RequestParam("people") String person){
        Resource resource=new Resource();
        resource.setCrtime(geTime());
        resource.setHostnum(0);
        resource.setName(name);
        resource.setPerson(person);
        try {
            iResourceService.resourceAdd(resource);
        }catch (Exception e) {
            e.printStackTrace();
            return Response.failure("添加失败", resource);
        }
        return Response.success("添加成功", resource);
    }

    //删除资源
    @RequestMapping("/delete")
    public Response resourceDeleteBatch(@RequestBody List<Resource> resList) {
        try {
            iResourceService.resourceDeleteBatch(resList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", resList);
        }
        return Response.success("删除成功", resList);
    }

    //删除单个资源
    @RequestMapping("/delete/{id}")
    public Response resourceDeleteOne(@PathVariable("id") Integer resId){
        try {
            iResourceService.resourceDelete(resId);
        }catch (Exception e) {
            e.printStackTrace();
            return Response.failure("删除失败", resId);
        }
        return Response.success("删除成功", resId);

    }

    @RequestMapping(value = "/hosts/{resId}", method = RequestMethod.POST)
    public Response crtHosts(HttpServletRequest request, @PathVariable("resId") String resId) {
        iResourceService.deleteAll(Integer.parseInt(resId));
        String hosts = request.getParameter("hostsdata");
        if (hosts == "")
            return Response.success("code", "-1");
        hosts = hosts.replaceAll("\\\\", "");
        hosts = hosts.substring(1, hosts.length() - 1);
        JSONObject json = JSONObject.parseObject(hosts);
        Iterator iter = json.keySet().iterator();
        int hostorder = 1;
        Map<Integer, Integer> map = new HashMap<>();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            String value = json.getString(key);
            JSONArray jsonArray = JSONArray.parseArray(value);
            List<Vm> vmList = new ArrayList<>();
            int number = 0;
            for (int i = 0; i < jsonArray.size(); i++) {
                Vm vm = new Vm();
                vm.setHostId(hostorder);
                vm.setResId(Integer.parseInt(resId));
                vm.setUser("susan");
                vm.setVmname(jsonArray.getJSONObject(i).getString("vmname").toString());
                vm.setCount(Integer.parseInt((jsonArray.getJSONObject(i).getString("num")).toString()));
                vm.setMirror(Integer.parseInt((jsonArray.getJSONObject(i).getString("size")).toString()));
                vm.setRam(Integer.parseInt((jsonArray.getJSONObject(i).getString("memo")).toString()));
                vm.setMips(Integer.parseInt((jsonArray.getJSONObject(i).getString("mips")).toString()));
                vm.setBw(Integer.parseInt((jsonArray.getJSONObject(i).getString("bw")).toString()));
                vm.setCpu(Integer.parseInt((jsonArray.getJSONObject(i).getString("cpu")).toString()));
                vm.setEnrollmentTime(geTime());
                vmList.add(vm);
                number += Integer.parseInt(jsonArray.getJSONObject(i).getString("num").toString());
            }
            map.put(hostorder, number);
            iVmService.save(vmList);
            hostorder++;

        }
        List<Host> hostList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Host host = new Host();
            host.setHostorder(entry.getKey());
            host.setResId(Integer.parseInt(resId));
            host.setVmnum(entry.getValue());
            host.setCrtime(geTime());
            host.setHostName("res" + resId + "Host" + entry.getKey());
            hostList.add(host);
        }
        iHostService.save(hostList);
        iResourceService.updateRes(resId,map.size());
       return Response.success("code", "1");
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

