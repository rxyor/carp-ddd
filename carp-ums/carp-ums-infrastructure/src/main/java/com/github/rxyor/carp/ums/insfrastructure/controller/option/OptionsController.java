package com.github.rxyor.carp.ums.insfrastructure.controller.option;

import com.github.rxyor.carp.query.dto.common.OptionDTO;
import com.github.rxyor.carp.ums.shared.interfaces.enums.AppIdEnum;
import com.github.rxyor.common.core.enums.KeyValue;
import com.github.rxyor.common.core.model.R;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 16:55:00
 * @since 1.0.0
 */
@Validated
@Api(tags = "Options")
@AllArgsConstructor
@RestController
@RequestMapping("/ums/option")
public class OptionsController {

    @ApiOperation("分页查询")
    @GetMapping("/app_id/list")
    public R<List<OptionDTO>> listAppId() {
        List<KeyValue> list = Lists.newArrayList(AppIdEnum.values());
        return R.success(OptionsMapper.convert(list));
    }
}
