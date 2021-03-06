package com.github.rxyor.carp.ums.insfrastructure.repository.user.dao;

import com.github.rxyor.carp.ums.insfrastructure.repository.user.dataobj.UserDO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDAO extends JpaSpecificationExecutor<UserDO>,
    JpaRepository<UserDO, Long> {

    @Query("from UserDO u where u.id=:id")
    UserDO find(@Param("id") Long id);

    @Query("from UserDO u where u.username=:username and u.disable=0")
    UserDO find(@Param("username") String username);

    @Query("from UserDO u where  u.disable=0")
    List<UserDO> findAll();

    List<UserDO> findByUsernameOrPhoneOrEmail(String username,
        String phone, String email);
}