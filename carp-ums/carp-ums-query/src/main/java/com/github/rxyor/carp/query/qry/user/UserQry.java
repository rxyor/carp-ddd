package com.github.rxyor.carp.query.qry.user;

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
public class UserQry extends PageQry {

    private Long id;
    private String username;
    private Integer disable;

}
