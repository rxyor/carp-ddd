package com.github.rxyor.carp.query.service.role;

import com.github.rxyor.carp.query.dto.role.RoleDTO;
import com.github.rxyor.carp.query.qry.role.RoleQry;
import org.springframework.data.domain.Page;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/18 周二 14:44:00
 * @since 1.0.0
 */
public interface RoleQryService {

    Page<RoleDTO> page(RoleQry qry);

}
