package com.gas.web.service;

import com.gas.web.dao.AlgorithmDao;
import com.gas.web.entity.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AlgorithmServiceImpI implements IAlgorithmService {
    private final AlgorithmDao algorithmDao;

    @Autowired
    public AlgorithmServiceImpI(AlgorithmDao algorithmDao) {
        this.algorithmDao = algorithmDao;
    }

    @Override
    public Map<String, Object> algorithmFindAll() {
        //构造返回的 JSON 注意有格式要求   参见  resources/static/api/table.json
        Map<String, Object> res = new HashMap<>();
        List<Algorithm> algorithmData = algorithmDao.findAll();
        String resCode = "0";
        if (algorithmData == null)
            resCode = "1";
        res.put("code", resCode);
        res.put("msg", "");
        res.put("count", algorithmData.size());
        res.put("data", algorithmData);
        return res;
    }

    @Override
    public Algorithm algorithmFindById(Integer id) {
        Optional<Algorithm> optional = algorithmDao.findById(id);
        return optional.orElse(null);
    }


    @Override
    public Algorithm algorithmAdd(Algorithm algorithm) {
        return algorithmDao.save(algorithm);
    }

    @Override
    public Algorithm algorithmUpdate(Algorithm algorithm) {
        return algorithmDao.save(algorithm);
    }

    @Override
    public void algorithmDelete(Integer id) {
        algorithmDao.deleteById(id);
    }

    @Override
    public void algorithmDeleteBatch(List<Algorithm> algorithmList) {
        List<Integer> idList = new ArrayList<>();
        for (Algorithm algorithm : algorithmList) {
            idList.add(algorithm.getId());
        }
        algorithmDao.deleteBatch(idList);
    }

    @Override
    public Map<String, Object> findByNameLike(String name) {
        Map<String, Object> res = new HashMap<>();
        List<Algorithm> algorithmData = new ArrayList<Algorithm>();
        List<Integer> algorithmId = algorithmDao.findByNameLike(name);
        for (Integer id: algorithmId) {
            algorithmData.add(algorithmFindById(id));
        }
        String resCode = "0";
        if (algorithmData == null)
            resCode = "1";
        res.put("code", resCode);
        res.put("msg", "");
        res.put("count", algorithmData.size());
        res.put("data", algorithmData);
        return res;
    }
}

