package com.github.rxyor.carp.ums.start.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/10/22 周二 12:57:00
 * @since 1.0.0
 */
@Configuration
public class DatasourceConfig {

    @Bean
    public DataSourceTransactionManager transactionManager(
        @Qualifier("dataSource") DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
