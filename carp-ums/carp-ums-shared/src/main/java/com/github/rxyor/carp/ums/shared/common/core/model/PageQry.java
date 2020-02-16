package com.github.rxyor.carp.ums.shared.common.core.model;

import com.github.rxyor.carp.ums.shared.common.core.consts.QryConst;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/17 周一 01:11:00
 * @since 1.0.0
 */
@Data
public class PageQry {

    /**
     * 页码从1开始
     */
    protected int page = QryConst.PAGE;

    /**
     * 每页显示数
     */
    protected int pageSize = QryConst.PAGE_SIZE;

    public PageQry() {
        this.page = QryConst.PAGE;
        this.pageSize = QryConst.PAGE_SIZE;
    }
}
