package com.gas.web.dao;

import com.gas.web.entity.Paper;
import com.gas.web.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PaperDao extends JpaRepository<Paper,Integer> {

    List<Paper> findByAuthor1(String author1);

    @Modifying
    @Transactional
    @Query(value = "delete s from paper s where s.id in (:idList)", nativeQuery = true)
    void deleteBatch(List<Integer> idList);
}
