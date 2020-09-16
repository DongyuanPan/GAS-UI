package com.gas.web.service;

import com.gas.web.dao.ResSampleDao;
import com.gas.web.entity.ResSample;
import com.gas.web.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ResSampleImpl implements IResSampleService {
    private final ResSampleDao resSampleDao;

    @Autowired
    public ResSampleImpl(ResSampleDao resSampleDao) {
        this.resSampleDao = resSampleDao;
    }

    @Override
    public ResSample resSampleUpdate(ResSample resample) {
        return resSampleDao.save(resample);
    }

    @Override
    public Map<String, Object> findByUser(String user) {
        Map<String, Object> res = new HashMap<>();
        ResSample resSample=null;
        List<Integer> resSampleId = resSampleDao.findByUser(user);
        resSample=sampleFindById(resSampleId.get(0));
        String resCode = "0";
        if (resSample == null)
            resCode = "1";
        res.put("code", resCode);
        res.put("msg", "");
        res.put("count", 1);
        res.put("data", resSample);
        return res;
    }

    @Override
    public ResSample sampleFindById(Integer id) {
        Optional<ResSample> optional = resSampleDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Map<String, Object> findAllSample() {
        //构造返回的 JSON 注意有格式要求   参见  resources/static/api/table.json
        Map<String, Object> res = new HashMap<>();
        List<ResSample> sampleData = resSampleDao.findAll();
        String resCode = "0";
        if (sampleData == null)
            resCode = "1";
        res.put("code", resCode);
        res.put("msg", "");
        res.put("count", sampleData.size());
        res.put("data", sampleData);
        return res;
    }

    @Override
    public void deleteBatch(List<ResSample> sampleList) {
        resSampleDao.deleteBatch(sampleList);
    }
}

