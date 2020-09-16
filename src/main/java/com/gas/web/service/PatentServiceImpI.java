package com.gas.web.service;

import com.gas.web.dao.PatentDao;
import com.gas.web.entity.Patent;
import com.gas.web.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.io.File;
import java.util.*;

@Service
public class PatentServiceImpI implements IPatentService {
    private final PatentDao patentDao;

    @Autowired
    public PatentServiceImpI(PatentDao patentDao) {
        this.patentDao = patentDao;
    }

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
        Patent patent = patentFindById(id);
        deleteFile(patent.getPath());
        patentDao.deleteById(id);
    }
    public void deleteFile(String fileName){
        File daxFile = new File("src/main/resources/PatentFile/" + fileName);
        daxFile.delete();
    }
    @Override
    public void patentDeleteBatch(List<Patent> patList) {
        List<Integer> idList = new ArrayList<>();
        for (Patent patent : patList) {
            idList.add(patent.getId());
        }
        patentDao.deleteBatch(idList);
    }

    @Override
    public Map<String, Object> findByNameLike(String name) {
        Map<String, Object> res = new HashMap<>();
        List<Patent> patentData=new ArrayList<Patent>();
        List<Integer> patentId = patentDao.findByNameLike(name);
        for (Integer id: patentId) {
            patentData.add(patentFindById(id));
        }
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
    public Map<String, Object> findByPatnameLike(String patname) {
        Map<String, Object> res = new HashMap<>();
        List<Patent> patentData=new ArrayList<Patent>();
        List<Integer> patentId = patentDao.findByPatnameLike(patname);
        for (Integer id: patentId) {
            patentData.add(patentFindById(id));
        }
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
    public Map<String, Object> findByDynamicCases(String name, String keyword) {
        Map<String, Object> res = new HashMap<>();
        List<Patent> patentData = patentDao.findAll(new Specification<Patent>() {
            @Override
            public Predicate toPredicate(Root<Patent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate1, predicate2;
                Path<String> secondname = root.get("secondname");
                Path<String> patname = root.get("patname");
                query.where(cb.like(secondname, "%" + name + "%"), cb.like(patname, "%" + keyword + "%"));
                return null;
            }
        });
        String resCode = "0";
        if (patentData == null)
            resCode = "1";
        res.put("code", resCode);
        res.put("msg", "");
        res.put("count", patentData.size());
        res.put("data", patentData);
        return res;
    }

}

