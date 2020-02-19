package com.github.rxyor.carp.query.qry.clientdetails;

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
 * @date 2020/2/19 周三 21:58:00
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Data
public class ClientQry extends PageQry {

    private Long id;
    private String client;

}
