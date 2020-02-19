package com.github.rxyor.carp.ums.insfrastructure.controller.option;

import com.github.rxyor.carp.query.dto.common.OptionDTO;
import com.github.rxyor.common.core.enums.KeyValue;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 17:02:00
 * @since 1.0.0
 */
@UtilityClass
public class OptionsMapper {


    public static OptionDTO convert(KeyValue source) {
        if (source == null) {
            return null;
        }

        OptionDTO o = new OptionDTO();
        if (source.getCode() != null) {
            o.setValue(source.getCode().toString());
        }
        o.setLabel(source.getDesc());
        return o;
    }

    public static List<OptionDTO> convert(List<KeyValue> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>(0);
        }
        return list.stream().map(OptionsMapper::convert).collect(Collectors.toList());
    }

}
