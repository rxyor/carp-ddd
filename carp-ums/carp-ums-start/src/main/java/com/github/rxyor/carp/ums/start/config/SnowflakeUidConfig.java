package com.github.rxyor.carp.ums.start.config;

import com.github.rxyor.snowflake.impl.DefaultUidGenerator;
import com.github.rxyor.snowflake.worker.DisposableWorkerIdAssigner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/10/10 周四 13:40:00
 * @since 1.0.0
 */
@MapperScan(basePackages = {
    "com.github.rxyor.snowflake.worker.dao"
})
@EnableCaching
@Configuration
public class SnowflakeUidConfig {

    private final static int TIME_BITS = 29;
    private final static int WORKER_BITS = 21;
    private final static int SEQ_BITS = 13;
    private final static String EPOCH_STR = "2019-10-10";

    @Bean
    public DisposableWorkerIdAssigner disposableWorkerIdAssigner() {
        return new DisposableWorkerIdAssigner();
    }

    @Lazy(value = false)
    @Bean
    public DefaultUidGenerator defaultUidGenerator(
        DisposableWorkerIdAssigner disposableWorkerIdAssigner) {
        DefaultUidGenerator generator = new DefaultUidGenerator();
        generator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        generator.setTimeBits(TIME_BITS);
        generator.setWorkerBits(WORKER_BITS);
        generator.setSeqBits(SEQ_BITS);
        generator.setEpochStr(EPOCH_STR);
        return generator;
    }

}
