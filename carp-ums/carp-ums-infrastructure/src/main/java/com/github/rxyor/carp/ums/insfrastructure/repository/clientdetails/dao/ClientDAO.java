package com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.dao;

import com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.dataobj.ClientDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 21:42:00
 * @since 1.0.0
 */
public interface ClientDAO extends JpaRepository<ClientDO, Long>
    , JpaSpecificationExecutor<ClientDO> {

    @Query("from ClientDO c where c.id = :id")
    ClientDO find(@Param("id") Long id);

}
