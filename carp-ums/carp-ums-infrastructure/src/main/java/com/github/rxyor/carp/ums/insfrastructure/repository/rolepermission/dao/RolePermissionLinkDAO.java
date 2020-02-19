package com.github.rxyor.carp.ums.insfrastructure.repository.rolepermission.dao;

import com.github.rxyor.carp.ums.insfrastructure.repository.rolepermission.dataobj.RolePermissionLinkDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RolePermissionLinkDAO extends JpaRepository<RolePermissionLinkDO, Long>,
    JpaSpecificationExecutor<RolePermissionLinkDO> {

    void deleteAllByPermissionId(Long id);
}