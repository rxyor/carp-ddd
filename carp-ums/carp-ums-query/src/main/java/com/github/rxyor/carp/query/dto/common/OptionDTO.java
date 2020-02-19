package com.github.rxyor.carp.query.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 17:00:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Data
public class OptionDTO {

    private String value;

    private String label;

    public OptionDTO() {
    }
}
