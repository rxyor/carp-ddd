package com.github.rxyor.carp.ddd.insfrastructure.controller.user;

import com.github.rxyor.common.core.model.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/27 周五 18:38:00
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/save")
    public R<Object> save(){
        return null;
    }
}
