package com.github.rxyor.carp.canal.message.processor.canal;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

@Data
public class CanalTestDO {

    private Long id;

    private String name;

    private String phone;

    private String province;

    private String city;

    private String street;

    @JSONField(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private Date createTime;

    @JSONField(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private Date updateTime;

}
