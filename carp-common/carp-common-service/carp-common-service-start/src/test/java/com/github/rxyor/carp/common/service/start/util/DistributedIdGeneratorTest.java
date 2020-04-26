package com.github.rxyor.carp.common.service.start.util;

import com.github.rxyor.carp.common.service.start.SpringWithJUnit5IT;
import org.junit.jupiter.api.Test;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/26 周日 14:02:00
 * @since 1.0.0
 */
class DistributedIdGeneratorTest extends SpringWithJUnit5IT {


    @Test
    void getIdBySegment() {
        int batch = 10000;
        long total=0L;
        for (int i = 0; i <batch; i++) {
            long s = System.currentTimeMillis();
            try {
                Long id = DistributedIdGenerator.getIdBySegment(BizTag.TEST);
            } catch (Exception e) {
                e.printStackTrace();
            }
            long e = System.currentTimeMillis();
            long diff=e-s;
            total += diff;
        }

        System.out.println(String.format("loop:[%s], total:[%s]ms",batch,total));
        System.out.println(String.format("loop:[%s], avg:[%s]ms",batch,total/batch));

    }

    @Test
    void getIdBySnowflake() {
    }
}