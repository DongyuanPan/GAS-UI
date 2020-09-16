package com.gas.web.service;

import com.gas.web.dao.HostDao;
import com.gas.web.dao.ResourceDao;
import com.gas.web.dao.VmDao;
import com.gas.web.entity.Resource;
import com.gas.web.entity.Vm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResourceServiceImpl implements IResourceService {
    private final ResourceDao resourceDao;
    private final HostDao hostDao;
    private final VmDao vmDao;

    @Autowired
    public ResourceServiceImpl(ResourceDao resourceDao, HostDao hostDao, VmDao vmDao) {
        this.resourceDao = resourceDao;
        this.hostDao = hostDao;
        this.vmDao = vmDao;
    }
    @Override
    public Map<String, Object> vmFindAll(Integer id) {
        Map<String, Object> res = new HashMap<>();
        List<List<Vm>> vmlists=new ArrayList<>();
        List<Integer> hostByResId = hostDao.getHostByResId(id);
        for (Integer hostid:hostByResId) {
            List<Integer> vmByHostId = vmDao.getVmByHostId(id, hostid);
            List<Vm> allById = vmDao.findAllById(vmByHostId);
            if(allById.size()!=0) {
                vmlists.add(allById);
            }
        }
        String resCode = "0";
        if (vmlists == null)
            resCode = "1";
        res.put("code", resCode);
        res.put("msg", "");
        res.put("count", vmlists.size());
        res.put("data", vmlists);
        return res;
    }

    @Override
    public void deleteAll(Integer resId) {
        List<Integer> resList=new ArrayList<>();
        resList.add(resId);
        hostDao.deleteByResId(resList);
        vmDao.deleteBatchByRes(resList);

    }

    @Override
    public void updateRes(String resId, int size) {
        resourceDao.updateRes(size,resId);
    }

    @Override
    public Map<String, Object> resourceFindAll() {
        //构造返回的 JSON 注意有格式要求   参见  resources/static/api/table.json
        Map<String, Object> res = new HashMap<>();
        List<Resource> resourceData = resourceDao.findAll();
        String resCode = "0";
        if (resourceData == null)
            resCode = "1";
        res.put("code", resCode);
        res.put("msg", "");
        res.put("count", resourceData.size());
        res.put("data", resourceData);
        return res;
    }

    @Override
    public Resource resourceFindById(Integer id) {
        Optional<Resource> optional = resourceDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Resource resourceAdd(Resource resource) {
        return resourceDao.save(resource);
    }

    @Override
    public Resource resourceUpdate(Resource resource) {
        return resourceDao.save(resource);
    }

    @Override
    public void resourceDelete(Integer id) {
        resourceDao.deleteById(id);
        List<Integer> resList=new ArrayList<>();
        resList.add(id);
        hostDao.deleteByResId(resList);
        vmDao.deleteBatchByRes(resList);
    }

    @Override
    public void resourceDeleteBatch(List<Resource> resList) {
        List<Integer> idList=new ArrayList<>();
        for (Resource resource:resList) {
            idList.add(resource.getId());
        }
        resourceDao.deleteBatch(idList);
        hostDao.deleteByResId(idList);
        vmDao.deleteBatchByRes(idList);
    }

    @Override
    public Map<String, Object> findByNameLike(String name) {
        Map<String, Object> res = new HashMap<>();
        List<Resource> resourceData=new ArrayList<Resource>();
        List<Integer> resourceId = resourceDao.findByNameLike(name);
        for (Integer id: resourceId) {
            resourceData.add(resourceFindById(id));
        }
        String resCode = "0";
        if (resourceData == null)
            resCode = "1";
        res.put("code", resCode);
        res.put("msg", "");
        res.put("count", resourceData.size());
        res.put("data", resourceData);
        return res;
    }


}

