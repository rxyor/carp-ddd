package com.github.rxyor.carp.ddd.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends
    CrudRepository<User, Long>, JpaRepository<User, Long> {

}