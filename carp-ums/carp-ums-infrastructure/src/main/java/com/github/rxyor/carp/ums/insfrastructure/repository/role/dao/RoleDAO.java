package com.github.rxyor.carp.ums.insfrastructure.repository.role.dao;

import com.github.rxyor.carp.ums.insfrastructure.repository.role.dataobj.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleDAO extends JpaRepository<RoleDO, Long>, JpaSpecificationExecutor<RoleDO> {

    @Query("from RoleDO r where r.id=:id")
    RoleDO find(@Param("id") Long id);
}