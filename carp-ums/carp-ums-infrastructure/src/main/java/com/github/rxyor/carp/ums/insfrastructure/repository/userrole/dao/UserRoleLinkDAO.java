package com.github.rxyor.carp.ums.insfrastructure.repository.userrole.dao;

import com.github.rxyor.carp.ums.insfrastructure.repository.userrole.dataobj.UserRoleLinkDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRoleLinkDAO extends JpaRepository<UserRoleLinkDO, Long>, JpaSpecificationExecutor<UserRoleLinkDO> {

}