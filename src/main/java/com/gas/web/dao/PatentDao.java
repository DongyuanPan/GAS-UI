package com.gas.web.dao;

import com.gas.web.entity.Patent;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PatentDao extends JpaRepository<Patent,Integer> {

    List<Patent> findByEnrollmentTime(String enrollmentTime);

    @Modifying
    @Transactional
    @Query(value = "delete s from patent s where s.id in (:idList)", nativeQuery = true)
    void deleteBatch(List<Integer> idList);
    @Query(value = "select p.id from patent p where p.secondname like %?1%", nativeQuery = true)
    List<Integer> findByNameLike(String name);
    @Query(value = "select p.id from patent p where p.patname like %?1%", nativeQuery = true)
    List<Integer> findByPatnameLike(String patname);
    List<Patent> findAll( Specification<Patent> specification);

}
