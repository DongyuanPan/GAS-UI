package com.gas.web.service;

import com.gas.web.entity.Paper;


import java.util.List;
import java.util.Map;

public interface IPaperService {
    Map<String, Object> paperFindAll();

    Paper paperFindById(Integer id);

    List<Paper> paperFindByAuthor(String author);

    Paper paperAdd(Paper paper);

    Paper paperUpdate(Paper paper);

    void paperDelete(Integer id);

    void paperDeleteBatch(List<Paper> papList);
}
