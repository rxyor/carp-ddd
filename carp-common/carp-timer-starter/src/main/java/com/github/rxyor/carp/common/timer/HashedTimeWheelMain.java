package com.github.rxyor.carp.common.timer;

import io.netty.util.HashedWheelTimer;
import io.netty.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/10 周五 10:45:00
 * @since 1.0.0
 */
public class HashedTimeWheelMain {

    public static void main(String[] args) throws Exception {
        HashedWheelTimer timer = new HashedWheelTimer(1, TimeUnit.SECONDS, 60);

        timer.start();

        int i = 0;
        while (true) {
            if (i % 10000 == 0) {
                Thread.sleep(3000);
            }
            int finalI = i;
            TimerTask task = timeout -> System.out.println(
                String.format("Thead:[%s] execute task:[%s]", Thread.currentThread().getName(), finalI));

            timer.newTimeout(task, i%1000, TimeUnit.SECONDS);
            i++;
        }
    }

}
