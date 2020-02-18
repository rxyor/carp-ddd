package com.github.rxyor.carp.query.service.permission;

import com.github.rxyor.carp.query.dto.permission.PermissionDTO;
import com.github.rxyor.carp.query.qry.permission.PermissionQry;
import java.util.List;
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
public interface PermissionQryService {

    Page<PermissionDTO> page(PermissionQry qry);

    PermissionDTO find(Long id);

    List<PermissionDTO> listEnable();

}
