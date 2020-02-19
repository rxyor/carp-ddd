package com.github.rxyor.carp.ums.domain.kvconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 14:07:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Data
public class KvList {

    @NotBlank(message = "key不能为空")
    private String key;

    @Valid
    @NotNull(message = "配置集不能为空")
    private List<KvConfig> list = new ArrayList<>(0);

    public KvConfig top() {
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    public String topValue() {
        KvConfig top = top();
        return top != null && StringUtils.isNotBlank(top.getValue()) ? top.getValue() : null;
    }

    public String topDesc() {
        KvConfig top = top();
        return top != null && StringUtils.isNotBlank(top.getDesc()) ? top.getDesc() : null;
    }

    public List<String> values() {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>(0);
        }
        return list.stream().map(KvConfig::getValue).filter(StringUtils::isNotBlank)
            .distinct().collect(Collectors.toList());
    }

}
