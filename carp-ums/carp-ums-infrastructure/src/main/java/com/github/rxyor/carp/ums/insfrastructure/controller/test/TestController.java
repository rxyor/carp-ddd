package com.github.rxyor.carp.ums.insfrastructure.controller.test;

import com.github.rxyor.carp.auth.security.util.AuthHolder;
import com.github.rxyor.common.core.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
@Validated
@Api(tags = "Test")
@AllArgsConstructor
@RestController
@RequestMapping("/ums/test")
public class TestController {

    @ApiOperation("异步获取上下文用户信息")
    @GetMapping("/security/async")
    public R<Object> asyncGetContextUser() throws Exception {

        final List<Object> list = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        Thread async = new Thread(() -> {
            try {
                list.add(AuthHolder.user());
            } finally {
                latch.countDown();
            }
        });
        async.start();
        latch.await();

        return R.success(list.isEmpty() ? null : list.get(0));
    }

    @ApiOperation("获取上下文用户信息")
    @GetMapping("/security/sync")
    public R<Object> getContextUser() {
        return R.success(AuthHolder.user());
    }

}
