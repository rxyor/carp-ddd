package com.github.rxyor.carp.common.service.start.util;

import static com.github.rxyor.carp.common.service.start.util.DistributedIdGenerator.SegmentFailEnum.EXCEPTION_ID_CACHE_INIT_FALSE;
import static com.github.rxyor.carp.common.service.start.util.DistributedIdGenerator.SegmentFailEnum.EXCEPTION_ID_KEY_NOT_EXISTS;
import static com.github.rxyor.carp.common.service.start.util.DistributedIdGenerator.SegmentFailEnum.EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL;
import static com.github.rxyor.carp.common.service.start.util.DistributedIdGenerator.SnowflakeFailEnum.CLOCK_SLOW_AND_WAIT_FAIL;
import static com.github.rxyor.carp.common.service.start.util.DistributedIdGenerator.SnowflakeFailEnum.CLOCK_SLOW_AND_WAIT_INTERRUPTED;
import static com.github.rxyor.carp.common.service.start.util.DistributedIdGenerator.SnowflakeFailEnum.CLOCK_SLOW_TOO_MUCH;

import com.github.rxyor.carp.leaf.common.Result;
import com.github.rxyor.carp.leaf.common.Status;
import com.github.rxyor.carp.leaf.service.SegmentService;
import com.github.rxyor.carp.leaf.service.SnowflakeService;
import com.github.rxyor.common.core.enums.KeyValue;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/26 周日 11:27:00
 * @since 1.0.0
 */
@Component
@Slf4j
public class DistributedIdGenerator implements InitializingBean {

    private static DistributedIdGenerator INSTANCE;

    private final SegmentService segmentService;

    private final SnowflakeService snowflakeService;

    public DistributedIdGenerator(SegmentService segmentService,
        SnowflakeService snowflakeService) {
        this.segmentService = segmentService;
        this.snowflakeService = snowflakeService;
    }

    private static DistributedIdGenerator getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("DistributedIdGenerator INSTANCE not ready, please wait bean be inited");
        }
        return INSTANCE;
    }

    /**
     *<p>
     * DB双Buffer分段获取唯一ID，ID从1~Long.MAX_VALUE
     *</p>
     *
     * @author liuyang
     * @date 2020-04-26 周日 13:35:39
     * @param bizTag bizTag
     * @return [Long]
     */
    public static Long getIdBySegment(BizTag bizTag) {
        Preconditions.checkNotNull(bizTag, "业务类型不能为空");

        return getIdBySegment(bizTag.getCode());
    }

    /**
     *<p>
     * DB双Buffer分段获取唯一ID，ID从1~Long.MAX_VALUE
     *</p>
     *
     * @author liuyang
     * @date 2020-04-26 周日 13:35:39
     * @param bizTag bizTag
     * @return [Long]
     */
    public static Long getIdBySegment(String bizTag) {
        if (StringUtils.isBlank(bizTag)) {
            throw new IllegalArgumentException("bizTag can't be blank");
        }

        //segment超过步长时，会读db, 取maxId, 连接DB可能会耗时较多
        //导致buffer初始化失败，因此db跟服务不在一个机房，会存在失败情况。
        //segment 等待buffer准备好的时间是10mills, 100次尝试差不多1秒，100次
        //尝试后仍然失败，就放弃
        int retryTimes = 100;
        Result result = getInstance().segmentService.getId(bizTag);
        while (!Status.SUCCESS.equals(result.getStatus())
            && EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL.getCode().equals(result.getId())
            && retryTimes-- > 0) {
            result = getInstance().segmentService.getId(bizTag);
        }

        if (EXCEPTION_ID_CACHE_INIT_FALSE.getCode().equals(result.getId())) {
            String msg = String.format("获取业务标识:[%s]的ID失败, IdCache未初始化, 请检查配置或检查Spring Bean初始化顺序", bizTag);
            log.error(msg);
            throw new RuntimeException(msg);
        } else if (EXCEPTION_ID_KEY_NOT_EXISTS.getCode().equals(result.getId())) {
            String msg = String.format("获取业务标识:[%s]的ID失败, 业务标识不存在, 请更新数据或重启服务", bizTag);
            log.error(msg);
            throw new RuntimeException(msg);
        } else if (EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL.getCode().equals(result.getId())) {
            if (retryTimes <= 0) {
                log.error("获取业务标识:[{}]的ID失败, 重试次数超过阈值", bizTag);
            }
            String msg = String.format("获取业务标识:[%s]的ID失败, DB操作延时较高, SegmentBuffer未准备就绪", bizTag);
            log.error(msg);
            throw new RuntimeException(msg);
        }

        return result.getId();
    }

    /**
     *<p>
     * Zookeeper 持久或服务节点的Snowflake分布式ID ,ID长度较长
     *</p>
     *
     * @author liuyang
     * @date 2020-04-26 周日 14:00:28
     * @param bizTag bizTag
     * @return [Long]
     */
    public static Long getIdBySnowflake(BizTag bizTag) {
        Preconditions.checkNotNull(bizTag, "业务类型不能为空");

        return getIdBySegment(bizTag.getCode());
    }

    /**
     *<p>
     * Zookeeper 持久或服务节点的Snowflake分布式ID ,ID长度较长
     *</p>
     *
     * @author liuyang
     * @date 2020-04-26 周日 14:00:28
     * @param bizTag bizTag
     * @return [Long]
     */
    public static Long getIdBySnowflake(String bizTag) {
        if (StringUtils.isBlank(bizTag)) {
            throw new IllegalArgumentException("bizTag can't be blank");
        }

        Result result = getInstance().snowflakeService.getId(bizTag);
        if (CLOCK_SLOW_AND_WAIT_FAIL.getCode().equals(result.getId())) {
            String msg = String.format("获取业务标识:[%s]的ID失败, 时钟不同步, 尝试等待后仍然不同步", bizTag);
            log.error(msg);
            throw new RuntimeException(msg);
        } else if (CLOCK_SLOW_AND_WAIT_INTERRUPTED.getCode().equals(result.getId())) {
            String msg = String.format("获取业务标识:[%s]的ID失败, 时钟不同步, 尝试等待线程被中断", bizTag);
            log.error(msg);
            throw new RuntimeException(msg);
        } else if (CLOCK_SLOW_TOO_MUCH.getCode().equals(result.getId())) {
            String msg = String.format("获取业务标识:[%s]的ID失败, 时钟不同步, 差值太大", bizTag);
            log.error(msg);
            throw new RuntimeException(msg);
        }

        return result.getId();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;
    }

    /**
     *<p>
     *
     *</p>
     *
     * @author liuyang
     * @date 2020/4/26 周日 15:55:00
     * @since 1.0.0
     */
    @ToString
    public enum BizTag implements KeyValue<String> {
        TEST("leaf-segment-test", "测试"),
        ;

        @Getter
        private final String code;

        @Getter
        private final String desc;

        BizTag(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     *<p>
     *
     *</p>
     *
     * @author liuyang
     * @date 2020/4/26 周日 15:55:00
     * @since 1.0.0
     */
    @ToString
    public enum SegmentFailEnum implements KeyValue<Long> {
        EXCEPTION_ID_CACHE_INIT_FALSE(-1L, "IDCache未初始化成功时的异常码"),
        EXCEPTION_ID_KEY_NOT_EXISTS(-2L, "key不存在时的异常码"),
        EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL(-3L, "SegmentBuffer中的两个Segment均未从DB中装载时的异常码"),
        ;

        @Getter
        private final Long code;

        @Getter
        private final String desc;

        SegmentFailEnum(Long code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     *<p>
     *
     *</p>
     *
     * @author liuyang
     * @date 2020/4/26 周日 15:55:00
     * @since 1.0.0
     */
    @ToString
    public enum SnowflakeFailEnum implements KeyValue<Long> {
        CLOCK_SLOW_AND_WAIT_FAIL(-1L, "服务系统时钟慢, 等待后仍然慢"),
        CLOCK_SLOW_AND_WAIT_INTERRUPTED(-2L, "服务系统时钟慢, 等待中断异常"),
        CLOCK_SLOW_TOO_MUCH(-3L, "服务节点系统时钟不同步, 差值太大"),
        ;

        @Getter
        private final Long code;

        @Getter
        private final String desc;

        SnowflakeFailEnum(Long code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
