package com.github.rxyor.carp.query.qry.role;

import com.github.rxyor.carp.ums.shared.common.core.model.PageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/16 周日 22:57:00
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Data
public class RoleQry extends PageQry {

    private Long id;
    private String roleCode;
    private String roleName;
    private Integer disable;

}
