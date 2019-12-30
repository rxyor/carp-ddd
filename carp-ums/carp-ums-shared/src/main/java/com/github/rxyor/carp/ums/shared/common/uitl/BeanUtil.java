package com.github.rxyor.carp.ums.shared.common.uitl;

import com.alibaba.fastjson.JSON;
import java.util.List;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 11:25:00
 * @since 1.0.0
 */
public class BeanUtil {

    private BeanUtil() {
    }

    public static <T> T clone(final T source, Class<T> destClass) {
        if (source == null) {
            return null;
        }
        String json = JSON.toJSONString(source);
        return JSON.parseObject(json, destClass);
    }

    public static <T> List<T> clone(List<T> sourceList, Class<T> destClass) {
        if (sourceList == null) {
            return null;
        }
        String json = JSON.toJSONString(sourceList);
        return JSON.parseArray(json, destClass);
    }

    public static <T> T copy(final Object source, Class<T> destClass) {
        if (source == null) {
            return null;
        }
        String json = JSON.toJSONString(source);
        return JSON.parseObject(json, destClass);
    }

    public static <T> List<T> copy(List<?> sourceList, Class<T> destClass) {
        if (sourceList == null) {
            return null;
        }
        String json = JSON.toJSONString(sourceList);
        return JSON.parseArray(json, destClass);
    }

}
