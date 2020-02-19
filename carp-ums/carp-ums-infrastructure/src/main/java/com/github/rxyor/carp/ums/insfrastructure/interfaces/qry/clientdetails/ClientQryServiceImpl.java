package com.github.rxyor.carp.ums.insfrastructure.interfaces.qry.clientdetails;

import com.github.rxyor.carp.query.dto.clientdetails.ClientDTO;
import com.github.rxyor.carp.query.dto.common.OptionDTO;
import com.github.rxyor.carp.query.qry.clientdetails.ClientQry;
import com.github.rxyor.carp.query.service.clientdetails.ClientQryService;
import com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.criteria.ClientCriteria;
import com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.dataobj.ClientDO;
import com.github.rxyor.carp.ums.insfrastructure.repository.premssion.dao.PermissionDAO;
import com.github.rxyor.carp.ums.insfrastructure.repository.premssion.dataobj.PermissionDO;
import com.github.rxyor.carp.ums.insfrastructure.repository.role.dao.RoleDAO;
import com.github.rxyor.carp.ums.insfrastructure.repository.role.dataobj.RoleDO;
import com.github.rxyor.carp.ums.shared.common.biz.consts.PrefixConst.Oauth2;
import com.github.rxyor.spring.boot.cacheablettl.CacheableTtl;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 22:29:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Service
public class ClientQryServiceImpl implements ClientQryService {

    private final ClientCriteria clientCriteria;
    private final RoleDAO roleDAO;
    private final PermissionDAO permissionDAO;

    @Override
    public Page<ClientDTO> page(ClientQry qry) {
        Page<ClientDO> page = clientCriteria.selectPage(qry);
        return ClientDTOAssembler.INSTANCE.clientDTOPage(page);
    }

    @Override
    public ClientDTO find(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        ClientDO clientDO = clientCriteria.dao().find(id);
        return ClientDTOAssembler.INSTANCE.clientDTO(clientDO);
    }

    @CacheableTtl(cacheNames = "ClientQryService", ttl = 3600, unless = "#result == null || #result.size() == 0 ")
    @Override
    public List<OptionDTO> listAuthorities(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return new ArrayList<>(0);
        }

        List<OptionDTO> list = new ArrayList<>(32);
        List<RoleDO> roleList = roleDAO.findAllByRoleCodeRightLike(keyword);
        List<PermissionDO> permissionList = permissionDAO
            .findAllByPermissionCodeRightLike(keyword);
        roleList.forEach(r -> list.add(new OptionDTO(
            Oauth2.ROLE + r.getRoleCode(), r.getRoleName())));
        permissionList.forEach(p -> list.add(new OptionDTO(
            p.getPermissionCode(), p.getPermissionName())));
        return list;
    }
}
