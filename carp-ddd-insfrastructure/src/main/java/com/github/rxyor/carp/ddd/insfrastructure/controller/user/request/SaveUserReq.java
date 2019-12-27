package com.github.rxyor.carp.ddd.insfrastructure.controller.user.request;

import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/27 周五 23:34:00
 * @since 1.0.0
 */
@Data
public class SaveUserReq {

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
    private String salt = "";

    /**
     * 备注
     */
    private String remark;
}
