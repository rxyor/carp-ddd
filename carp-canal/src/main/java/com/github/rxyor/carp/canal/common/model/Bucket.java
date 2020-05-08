package com.github.rxyor.carp.canal.common.model;

import lombok.Data;

@Data
public class Bucket<T> {

    private String database;
    private String table;

    private String sqlType;

    private String id;

    private T data;


}
