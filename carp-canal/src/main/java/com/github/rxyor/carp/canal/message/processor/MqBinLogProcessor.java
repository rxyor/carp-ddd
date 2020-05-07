package com.github.rxyor.carp.canal.message.processor;

import com.github.rxyor.carp.canal.common.model.MqBinLog;

public interface MqBinLogProcessor<T> {

    boolean accept(String database, String table);

    MqBinLog<T> process(MqBinLog<String> binLog);

}
