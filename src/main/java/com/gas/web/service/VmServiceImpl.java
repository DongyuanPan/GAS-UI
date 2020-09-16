package com.gas.web.service;

import com.gas.web.dao.VmDao;
import com.gas.web.entity.Vm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class VmServiceImpl implements IVmService{
    private final VmDao vmDao;
    private final IResourceService iResourceService;
    @Autowired
    public VmServiceImpl(VmDao vmDao, IResourceService iResourceService) {
        this.vmDao = vmDao;
        this.iResourceService = iResourceService;
    }

    @Override
    public void save(List<Vm> vmList) {
        vmDao.saveAll(vmList);
    }


    //依据id查找资源下所有虚拟机
    @Override
    public Map<String, Vm> getAllVm(Integer id) {
        int vmnumber=0;
        Map<String, Vm> map=new HashMap<>();
        Map<String, Object> stringObjectMap = iResourceService.vmFindAll(id);
        List<List<Vm>> data = (List<List<Vm>>) stringObjectMap.get("data");
        for (List<Vm> vmList:data) {
            for (Vm vm:vmList) {
                map.put(String.valueOf(vmnumber),vm);
                vmnumber+=vm.getCount();
            }
        }
        return map;
    }
}
