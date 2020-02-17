package com.github.rxyor.carp.ums.insfrastructure.controller.user.request;

import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/17 周一 23:11:00
 * @since 1.0.0
 */
@Data
public class UpdateUserReq {

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String nickname;

    private String avatar;

    private Integer disable;

    private Integer locked;

    private String remark;
}
