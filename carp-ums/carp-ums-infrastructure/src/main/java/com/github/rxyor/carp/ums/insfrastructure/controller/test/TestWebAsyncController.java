package com.github.rxyor.carp.ums.insfrastructure.controller.test;

import com.alibaba.fastjson.JSON;
import com.github.rxyor.carp.auth.security.support.security.core.Oauth2User;
import com.github.rxyor.carp.auth.security.util.AuthHolder;
import com.github.rxyor.common.core.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/3/6 周五 17:41:00
 * @since 1.0.0
 */
@Slf4j
@Validated
@Api(tags = "Test-WebAsync")
@AllArgsConstructor
@RestController
@RequestMapping("/ums/test/security/web_async")
public class TestWebAsyncController {

    @ApiOperation("[WebAsyncTask]异步获取上下文用户信息")
    @GetMapping("/web_async_task")
    public WebAsyncTask getContextByWebAsyncTask() {
        log.info("1:main线程开始[{}]", Thread.currentThread());
        WebAsyncTask<Oauth2User> task = new WebAsyncTask<>(300L, () -> {
            log.info("1:WebAsyncTask异步线程[{}], 开始执行异步任务", Thread.currentThread().getName());
            Oauth2User user = AuthHolder.user();
            log.info("2:WebAsyncTask异步线程[{}], 任务执行完成, 结果:{}",
                Thread.currentThread().getName(), JSON.toJSONString(user));
            return user;
        });

        log.info("2:main线程结束[{}]", Thread.currentThread());
        return task;
    }

    @ApiOperation("[Runnable]异步获取上下文用户信息")
    @GetMapping("/runnable")
    public R<Oauth2User> getContextByRunnable() throws Exception {
        log.info("1:main线程开始[{}]", Thread.currentThread());
        final List<Oauth2User> list = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        Thread async = new Thread(() -> {
            try {
                log.info("1:Runnable异步线程[{}], 开始执行异步任务", Thread.currentThread().getName());
                Oauth2User user = AuthHolder.user();
                list.add(user);
                log.info("2:Runnable异步线程[{}], 任务执行完成, 结果:{}",
                    Thread.currentThread().getName(), JSON.toJSONString(user));
            } finally {
                latch.countDown();
            }
        });
        async.start();
        latch.await();
        log.info("2:main线程结束[{}]", Thread.currentThread());
        return R.success(list.isEmpty() ? null : list.get(0));
    }
}
