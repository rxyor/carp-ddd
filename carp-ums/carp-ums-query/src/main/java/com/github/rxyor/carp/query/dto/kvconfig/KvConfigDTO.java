package com.github.rxyor.carp.query.dto.kvconfig;

import java.util.Date;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 14:42:00
 * @since 1.0.0
 */
@Data
public class KvConfigDTO {

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private String value;

    /**
     * 描述或备注
     */
    private String desc;

    /**
     * 描述或备注
     */
    private String appId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
