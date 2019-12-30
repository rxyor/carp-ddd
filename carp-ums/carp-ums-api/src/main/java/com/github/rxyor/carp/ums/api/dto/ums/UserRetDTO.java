package com.github.rxyor.carp.ums.api.dto.ums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 12:48:00
 * @since 1.0.0
 */
@Data
public class UserRetDTO implements Serializable {

    private static final long serialVersionUID = 6737706257026877445L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 随机盐
     */
    private String salt;

    /**
     * 禁用标识[0:启用, 1:禁用]
     */
    private Integer disable;

    /**
     * 锁定标识[0:未锁定, 1:锁定]
     */
    private Integer locked;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 角色列表
     */
    private List<RoleRetDTO> roleList = new ArrayList<>(8);
}
