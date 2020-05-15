package com.gas.web.service;

import com.gas.web.dao.PatentDao;
import com.gas.web.entity.Patent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatentServiceImpI implements IPatentService{
    private final PatentDao patentDao;

    @Autowired
    public PatentServiceImpI(PatentDao patentDao) { this.patentDao = patentDao; }

    @Override
    public Map<String, Object> patentFindAll() {
        //构造返回的 JSON 注意有格式要求   参见  resources/static/api/table.json
        Map<String, Object> res = new HashMap<>();
        List<Patent> patentData = patentDao.findAll();
        String resCode = "0";
        if (patentData == null)
            resCode = "1";
        res.put("code", resCode);
        res.put("msg", "");
        res.put("count", patentData.size());
        res.put("data", patentData);
        return res;
    }

    @Override
    public Patent patentFindById(Integer id) {
        Optional<Patent> optional = patentDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Patent> patentFindByAge(String enrollmentTime) {
        return patentDao.findByEnrollmentTime(enrollmentTime);
    }

    @Override
    public Patent patentAdd(Patent patent) {
        return patentDao.save(patent);
    }

    @Override
    public Patent patentUpdate(Patent patent) {
        return patentDao.save(patent);
    }

    @Override
    public void patentDelete(Integer id) {
        patentDao.deleteById(id);
    }

    @Override
    public void patentDeleteBatch(List<Patent> patList) {
        List<Integer> idList = new ArrayList<>();
        for ( Patent patent : patList) {
            idList.add(patent.getId());
        }
        patentDao.deleteBatch(idList);
    }
}
