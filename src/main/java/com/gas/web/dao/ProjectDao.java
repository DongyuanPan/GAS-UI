package com.gas.web.dao;

import com.gas.web.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProjectDao extends JpaRepository<Project,Integer> {


    @Modifying
    @Transactional
    @Query(value = "delete s from project s where s.id in (:idList)", nativeQuery = true)
    void deleteBatch(List<Integer> idList);
    @Query(value = "select p.id from project p where p.title like %?1%", nativeQuery = true)
    List<Integer> findByTitle(String title);
}
