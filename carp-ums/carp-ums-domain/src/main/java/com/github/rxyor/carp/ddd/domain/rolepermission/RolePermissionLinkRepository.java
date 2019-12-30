package com.github.rxyor.carp.ddd.domain.rolepermission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RolePermissionLinkRepository extends JpaRepository<RolePermissionLink, Integer>, JpaSpecificationExecutor<RolePermissionLink> {

}