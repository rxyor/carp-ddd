package com.github.rxyor.carp.ddd.domain.premssion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository
    extends JpaRepository<Permission, Long>, CrudRepository<Permission, Long> {

}