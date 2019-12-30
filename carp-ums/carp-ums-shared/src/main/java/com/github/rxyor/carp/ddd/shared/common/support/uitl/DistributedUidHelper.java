package com.github.rxyor.carp.ddd.shared.common.support.uitl;

import com.github.rxyor.common.support.spring.context.SpringBeanHolder;
import com.github.rxyor.snowflake.UidGenerator;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/10/10 周四 17:44:00
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class DistributedUidHelper {

    private static UidGenerator generator = SpringBeanHolder.getBean(UidGenerator.class);

    private DistributedUidHelper() {
    }

    /**
     *<p>
     *snowflake 生成uid
     *</p>
     *
     * @author liuyang
     * @date 2019-10-10 周四 19:33:51
     * @return
     */
    public static Long nextUID() {
        return generator.getUID();
    }

    /**
     *<p>
     * snowflake uid 解析，例：snowflake id =>
     * {"UID":"1207813523152920","timestamp":"2019-10-10 19:31:44",
     * "workerId":"5","sequence":"24"}
     *</p>
     *
     * @author liuyang
     * @date 2019-10-10 周四 19:35:01
     * @param uid uid
     * @return
     */
    public static String parseUID(Long uid) {
        return generator.parseUID(uid);
    }

}
