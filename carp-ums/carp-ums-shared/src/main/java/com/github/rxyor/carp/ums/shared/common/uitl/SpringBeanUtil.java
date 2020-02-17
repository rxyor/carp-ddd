package com.github.rxyor.carp.ums.shared.common.uitl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/18 周二 00:09:00
 * @since 1.0.0
 */
@UtilityClass
public class SpringBeanUtil {

    public static void copyIgnoreNull(Object source, Object target) {
        Objects.requireNonNull(source, "source can't be null");
        Objects.requireNonNull(target, "target can't be null");
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
