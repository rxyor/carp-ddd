package com.github.rxyor.carp.ddd.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends
    CrudRepository<User, Long>, JpaRepository<User, Long> {

    @Query("from User u where u.username=:username and u.disable=0")
    User find(@Param("username") String username);

}