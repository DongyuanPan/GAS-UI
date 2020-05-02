package com.gas.web.reposiroty;

import com.gas.web.entity.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysMenuRepository extends JpaRepository<SysMenu, Long> {

    //这里我只查询页面转态为启用,可自行定义和写
    @Query(value = "select * from  permission_security.system_menu where STATUS = 1  ORDER BY  sort ",nativeQuery = true)
    List<SysMenu> getSystemMenuByStatusAndSort(Long status, Integer sort);

    List<SysMenu> findAllByStatusOrderBySort(Boolean status);
}