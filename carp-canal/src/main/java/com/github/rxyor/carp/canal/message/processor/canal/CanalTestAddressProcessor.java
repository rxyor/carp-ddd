package com.github.rxyor.carp.canal.message.processor.canal;

import com.alibaba.fastjson.JSON;
import com.github.rxyor.carp.canal.common.model.MqBinLog;
import com.github.rxyor.carp.canal.ealsticsearch.CanalTestAddressEsService;
import com.github.rxyor.carp.canal.message.processor.AbstractMqBinLogProcessor;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class CanalTestAddressProcessor extends AbstractMqBinLogProcessor<CanalTestDO> {

    private final static String DATABASE = "canal_test";
    private final static String TABLE = "address";

    @Resource
    private CanalTestAddressEsService canalTestAddressEsService;

    @Override
    public boolean accept(String database, String table) {
        return DATABASE.equalsIgnoreCase(database) && TABLE.equalsIgnoreCase(table);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MqBinLog<CanalTestDO> process(MqBinLog<String> binLog) {
        if (binLog == null) {
            return null;
        }

        List<CanalTestDO> data = binLog.getData().stream().map(s -> JSON.parseObject(s, CanalTestDO.class))
            .collect(Collectors.toList());
        List<CanalTestDO> old = binLog.getOld().stream().map(s -> JSON.parseObject(s, CanalTestDO.class))
            .collect(Collectors.toList());
        MqBinLog<CanalTestDO> newLog = (MqBinLog<CanalTestDO>) binLog.copy();
        newLog.setData(data);
        newLog.setOld(old);

        doBusiness(newLog);
        return newLog;
    }


    public void doBusiness(MqBinLog<CanalTestDO> binLog){
        canalTestAddressEsService.save(binLog.getData());
    }
}
