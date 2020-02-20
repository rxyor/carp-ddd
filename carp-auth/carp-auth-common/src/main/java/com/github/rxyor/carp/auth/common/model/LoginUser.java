package com.github.rxyor.carp.auth.common.model;

import java.util.List;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/16 周日 19:19:00
 * @since 1.0.0
 */
@Data
public class LoginUser {

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色、权限Code
     */
    private List<String> resources;

    private List<Options> roleList;

    private List<Options> permissionList;
}
