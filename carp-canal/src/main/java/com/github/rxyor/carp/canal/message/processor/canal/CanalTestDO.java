package com.github.rxyor.carp.canal.message.processor.canal;

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

    private Date createTime;

    private Date updateTime;

}
