package com.github.rxyor.carp.ums.insfrastructure.repository.role.dao;

import com.github.rxyor.carp.ums.insfrastructure.repository.role.dataobj.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleDAO extends JpaRepository<RoleDO, Long>, CrudRepository<RoleDO, Long> {

}