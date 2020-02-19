package com.github.rxyor.carp.ums.insfrastructure.repository.kvconfig;

import com.github.rxyor.carp.ums.domain.kvconfig.IKvConfigRepository;
import com.github.rxyor.carp.ums.domain.kvconfig.KvConfig;
import com.github.rxyor.carp.ums.domain.kvconfig.KvList;
import com.github.rxyor.carp.ums.insfrastructure.repository.kvconfig.dataobj.KvConfigDO;
import com.github.rxyor.carp.ums.insfrastructure.repository.kvconfig.dao.KvConfigDAO;
import com.github.rxyor.carp.ums.insfrastructure.repository.kvconfig.serializer.KvConfigSerializer;
import com.github.rxyor.common.support.hibernate.validate.HibValidatorHelper;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 14:02:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Repository
public class KvConfigRepository implements IKvConfigRepository {

    private final KvConfigDAO kvConfigDAO;

    @Override
    public KvConfig find(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");
        KvConfigDO kvConfigDO = kvConfigDAO.find(id);
        return KvConfigSerializer.INSTANCE.deserialize(kvConfigDO);
    }

    @Override
    public List<KvConfig> findByKeyAndAppId(String key, String appId) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(appId)) {
            return new ArrayList<>(0);
        }

        List<KvConfigDO> list = kvConfigDAO.findAllByKeyAndAppId(key, appId);
        return KvConfigSerializer.INSTANCE.deserialize(list);
    }

    @Override
    public KvList findListByKeyAndAppId(String key, String appId) {
        if (StringUtils.isBlank(key)) {
            return new KvList("", new ArrayList<>(0));
        }
        List<KvConfig> list = findByKeyAndAppId(key, appId);
        return new KvList(key, list);
    }

    @Override
    public void save(KvConfig kvConfig) {
        Preconditions.checkArgument(kvConfig != null,
            "实体不能为空");
        HibValidatorHelper.validate(kvConfig);

        KvConfigDO entity = new KvConfigDO();
        BeanUtils.copyProperties(kvConfig, entity);
        kvConfigDAO.save(entity);
    }

    @Override
    public void delete(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        kvConfigDAO.deleteById(id);
    }

    @Override
    public void deleteByKeyAndAppId(String key, String appId) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key),
            "key不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(appId),
            "appId不能为空");

        kvConfigDAO.findAllByKeyAndAppId(key, appId);
    }
}
