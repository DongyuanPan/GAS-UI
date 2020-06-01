package com.gas.web.service;

import com.gas.web.dao.PaperDao;

import com.gas.web.entity.Paper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaperServiceImpl implements IPaperService {

    private final PaperDao paperDao;

    @Autowired
    public PaperServiceImpl(PaperDao paperDao) {
        this.paperDao = paperDao;
    }

    @Override
    public Map<String, Object> paperFindAll() {
        //构造返回的 JSON 注意有格式要求   参见  resources/static/api/table.json
        Map<String, Object> res = new HashMap<>();
        List<Paper> paperData = paperDao.findAll();
        String resCode = "0";
        if (paperData == null)
            resCode = "1";
        res.put("code", resCode);
        res.put("msg", "");
        res.put("count", paperData.size());
        res.put("data", paperData);
        return res;
    }

    @Override
    public Paper paperFindById(Integer id) {
        Optional<Paper> optional = paperDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Paper> paperFindByAuthor(String author) {
        return paperDao.findByAuthor1(author);
    }

    @Override
    public Paper paperAdd(Paper paper) {
        return paperDao.save(paper);
    }

    @Override
    public Paper paperUpdate(Paper paper) {
        return paperDao.save(paper);
    }

    @Override
    public void paperDelete(Integer id) {
        paperDao.deleteById(id);
    }

    @Override
    public void paperDeleteBatch(List<Paper> papList) {
        List<Integer> idList = new ArrayList<>();
        for (Paper paper : papList) {
            idList.add(paper.getId());
        }
        paperDao.deleteBatch(idList);
    }
}
