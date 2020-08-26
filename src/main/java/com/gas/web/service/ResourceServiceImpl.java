package com.gas.web.service;

import com.gas.web.dao.ResourceDao;
import com.gas.web.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResourceServiceImpl implements IResourceService {
    private final ResourceDao resourceDao;

    @Autowired
    public ResourceServiceImpl(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
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
    }

    @Override
    public void resourceDeleteBatch(List<Resource> resList) {
        List<Integer> idList=new ArrayList<>();
        for (Resource resource:resList) {
            idList.add(resource.getId());
        }
        resourceDao.deleteBatch(idList);
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

