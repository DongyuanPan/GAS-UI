package com.gas.web.dao;

import com.gas.web.entity.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.util.List;

public interface HostDao extends JpaRepository<Host,Integer> {
    @Modifying
    @Transactional
    @Query(value = "delete h from host h where h.hostorder in (:idList)", nativeQuery = true)
    void deleteBatch(List<Integer> idList);
    @Modifying
    @Transactional
    @Query(value="delete h from host h where h.hostorder=?1 and h.resId=?2", nativeQuery=true)
    void deleteByRes(Integer hostid, Integer resId);
    @Modifying
    @Transactional
    @Query(value="delete h from host h where h.resId in (:resList)", nativeQuery=true)
    void deleteByResId(List<Integer> resList);
    @Query(value = "select hostorder from host h where h.resId =?1", nativeQuery = true)
    List<Integer> getHostByResId(Integer id);


}
