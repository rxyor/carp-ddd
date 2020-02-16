package com.github.rxyor.carp.ums.shared.common.uitl;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/16 周日 23:48:00
 * @since 1.0.0
 */
@UtilityClass
public class SqlUtil {

    public static String leftLike(String s) {
        if (StringUtils.isBlank(s)) {
            return "%";
        }
        return "%" + s;
    }

    public static String rightLike(String s) {
        if (StringUtils.isBlank(s)) {
            return "%";
        }
        return s + "%";
    }

    public static String allLike(String s) {
        if (StringUtils.isBlank(s)) {
            return "%";
        }
        return "%" + s + "%";
    }
}
