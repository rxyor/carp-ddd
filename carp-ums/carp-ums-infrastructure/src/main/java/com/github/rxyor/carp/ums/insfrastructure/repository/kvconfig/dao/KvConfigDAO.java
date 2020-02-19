package com.github.rxyor.carp.ums.insfrastructure.repository.kvconfig.dao;

import com.github.rxyor.carp.ums.insfrastructure.repository.kvconfig.dataobj.KvConfigDO;
import java.util.List;
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
 * @date 2020/2/19 周三 13:55:00
 * @since 1.0.0
 */
public interface KvConfigDAO extends JpaRepository<KvConfigDO, Long>,
    JpaSpecificationExecutor<KvConfigDO> {

    @Query("from KvConfigDO c where c.id = :id")
    KvConfigDO find(@Param("id") Long id);

    List<KvConfigDO> findAllByKeyAndAppId(String key, String appId);

    void deleteAllByKeyAndAppId(String key, String appId);
}
