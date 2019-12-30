package com.github.rxyor.carp.ums.insfrastructure.repository.premssion.dao;

import com.github.rxyor.carp.ums.insfrastructure.repository.premssion.dataobj.PermissionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PermissionDAO
    extends JpaRepository<PermissionDO, Long>, CrudRepository<PermissionDO, Long> {

}