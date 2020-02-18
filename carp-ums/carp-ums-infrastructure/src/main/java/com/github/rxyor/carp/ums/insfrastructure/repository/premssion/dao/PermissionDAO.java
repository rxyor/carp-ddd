package com.github.rxyor.carp.ums.insfrastructure.repository.premssion.dao;

import com.github.rxyor.carp.ums.insfrastructure.repository.premssion.dataobj.PermissionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionDAO
    extends JpaRepository<PermissionDO, Long>, JpaSpecificationExecutor<PermissionDO> {

    @Query("from PermissionDO  p where p.id=:id")
    PermissionDO find(@Param("id") Long id);
}