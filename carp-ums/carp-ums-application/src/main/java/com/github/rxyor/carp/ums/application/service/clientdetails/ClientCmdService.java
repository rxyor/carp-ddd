package com.github.rxyor.carp.ums.application.service.clientdetails;

import com.github.rxyor.carp.ums.application.command.clientdetails.SaveClientCmd;
import com.github.rxyor.carp.ums.application.command.clientdetails.UpdateClientCmd;
import com.github.rxyor.carp.ums.domain.clientdetails.Client;
import com.github.rxyor.carp.ums.domain.clientdetails.IClientRepository;
import com.github.rxyor.carp.ums.shared.common.uitl.BeanUtil;
import com.github.rxyor.common.support.hibernate.validate.HibValidatorHelper;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
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
public class ClientCmdService {

    private final IClientRepository clientRepository;

    /**
     * save client
     *
     * @param cmd
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(SaveClientCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "命令不能为空");
        HibValidatorHelper.validate(cmd);

        Client client = ClientMapper.INSTANCE.from(cmd);
        clientRepository.save(client);
    }

    /**
     * update client
     *
     * @param cmd
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateClientCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "命令不能为空");

        Client client = BeanUtil.copy(cmd, Client.class);
        clientRepository.update(client);
    }

    public void delete(Long id) {
        Preconditions.checkArgument(id != null,
            "用户id不能为空");

        clientRepository.delete(id);
    }

}
