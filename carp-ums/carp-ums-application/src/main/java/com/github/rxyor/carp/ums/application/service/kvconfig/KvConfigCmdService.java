package com.github.rxyor.carp.ums.application.service.kvconfig;

import com.github.rxyor.carp.ums.application.command.kvconfig.SaveKvConfigCmd;
import com.github.rxyor.carp.ums.application.command.kvconfig.UpdateKvConfigCmd;
import com.github.rxyor.carp.ums.domain.kvconfig.IKvConfigRepository;
import com.github.rxyor.carp.ums.domain.kvconfig.KvConfig;
import com.github.rxyor.carp.ums.shared.common.uitl.SpringBeanUtil;
import com.github.rxyor.carp.ums.shared.interfaces.enums.AppIdEnum;
import com.github.rxyor.common.core.enums.KeyValue;
import com.github.rxyor.common.core.exception.BizException;
import com.github.rxyor.common.support.hibernate.validate.HibValidatorHelper;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/27 周五 23:11:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Service
public class KvConfigCmdService {

    private final IKvConfigRepository kvConfigRepository;

    /**
     * save kvConfig
     *
     * @param cmd
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(SaveKvConfigCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "命令不能为空");
        if (KeyValue.Util.getEnumByCode(cmd.getAppId(), AppIdEnum.class) == null) {
            throw new IllegalArgumentException(String.format("应用标识[%s]不存在", cmd.getAppId()));
        }
        HibValidatorHelper.validate(cmd);

        KvConfig kvConfig = KvConfigMapper.INSTANCE.from(cmd);
        kvConfigRepository.save(kvConfig);
    }

    /**
     * update kvConfig
     *
     * @param cmd
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateKvConfigCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "命令不能为空");
        if (StringUtils.isNotBlank(cmd.getAppId())
            && KeyValue.Util.getEnumByCode(cmd.getAppId(), AppIdEnum.class) == null) {
            throw new IllegalArgumentException(String.format("应用标识[%s]不存在", cmd.getAppId()));
        }
        HibValidatorHelper.validate(cmd);

        KvConfig dbKvConfig = kvConfigRepository.find(cmd.getId());
        if (dbKvConfig == null) {
            throw new BizException(
                String.format("id[%s]配置不存在", cmd.getId()));
        }

        KvConfig kvConfig = KvConfigMapper.INSTANCE.from(cmd);
        SpringBeanUtil.copyIgnoreNull(kvConfig, dbKvConfig);
        kvConfigRepository.save(dbKvConfig);
    }

    public void delete(Long id) {
        Preconditions.checkArgument(id != null,
            "用户id不能为空");

        kvConfigRepository.delete(id);
    }

}
