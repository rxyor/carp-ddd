package com.github.rxyor.carp.ums.domain.kvconfig;

import java.util.List;

public interface IKvConfigRepository {

    KvConfig find(Long id);

    List<KvConfig> findByKeyAndAppId(String key, String appId);

    KvList findListByKeyAndAppId(String key, String appId);

    void save(KvConfig entity);

    void delete(Long id);

    void deleteByKeyAndAppId(String key,String appId);
}