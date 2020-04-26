package com.github.rxyor.carp.common.service.start.util;

import com.github.rxyor.carp.common.service.start.SpringWithJUnit5IT;
import java.util.HashSet;
import java.util.Set;
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
        int batch = 100000;
        long total=0L;
        Set<Long> distinct = new HashSet<>(batch);
        for (int i = 0; i <batch; i++) {
            long s = System.currentTimeMillis();
            try {
                Long id = DistributedIdGenerator.getIdBySegment(DistributedIdGenerator.BizTag.TEST);
                distinct.add(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            long e = System.currentTimeMillis();
            long diff=e-s;
            total += diff;
        }

        System.out.println(String.format("loop:[%s], size:[%s]",batch,distinct.size()));
        System.out.println(String.format("loop:[%s], total:[%s]ms",batch,total));
        System.out.println(String.format("loop:[%s], avg:[%s]ms",batch,total/batch));

    }

    @Test
    void getIdBySnowflake() {
        int batch = 100000;
        long total=0L;
        Set<Long> distinct = new HashSet<>(batch);
        for (int i = 0; i <batch; i++) {
            long s = System.currentTimeMillis();
            try {
                Long id = DistributedIdGenerator.getIdBySnowflake(DistributedIdGenerator.BizTag.TEST);
                distinct.add(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            long e = System.currentTimeMillis();
            long diff=e-s;
            total += diff;
        }

        System.out.println(String.format("loop:[%s], size:[%s]",batch,distinct.size()));
        System.out.println(String.format("loop:[%s], total:[%s]ms",batch,total));
        System.out.println(String.format("loop:[%s], avg:[%s]ms",batch,total/batch));
    }
}