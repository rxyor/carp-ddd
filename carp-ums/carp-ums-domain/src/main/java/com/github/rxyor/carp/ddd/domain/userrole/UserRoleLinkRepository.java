package com.github.rxyor.carp.ddd.domain.userrole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRoleLinkRepository extends JpaRepository<UserRoleLink, Integer>, JpaSpecificationExecutor<UserRoleLink> {

}