package com.github.rxyor.carp.canal.common.model;

import java.util.List;
import lombok.Data;

/**
 * Canal RocketMq binlog字段映射类
 *
 * @param <T>
 */
@Data
public class MqBinLog<T> {

    /**
     * 数据列表
     */
    private List<T> data;

    /**
     * 旧数据列表
     */
    private List<T> old;

    /**
     * 变更数据库
     */
    private String database;

    /**
     * 表名
     */
    private String table;

    /**
     * 主键字段名称列表
     */
    private List<String> pkNames;

    /**
     * ddl语句[删除、建表表等]
     */
    private Boolean isDdl;

    /**
     * 类型:INSERT/UPDATE/DELETE
     */
    private String type;

    /**
     * 执行的sql,dml sql为空
     */
    private String sql;

    /**
     * binlog executeTime, 执行耗时
     */
    private Long es;

    /**
     * dml build timeStamp, 同步时间
     */
    private Long ts;

    public static enum SqlType {
        INSERT, UPDATE, DELETE, REPLACE
    }
}
