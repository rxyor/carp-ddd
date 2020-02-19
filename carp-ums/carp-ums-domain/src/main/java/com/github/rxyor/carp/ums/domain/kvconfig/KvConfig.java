package com.github.rxyor.carp.ums.domain.kvconfig;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 14:03:00
 * @since 1.0.0
 */
@Data
public class KvConfig {

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 键
     */
    @NotBlank(message = "key不能为空")
    private String key;

    /**
     * 值
     */
    @NotBlank(message = "value不能为空")
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
