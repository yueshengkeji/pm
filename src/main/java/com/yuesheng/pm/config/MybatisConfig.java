package com.yuesheng.pm.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MybatisConfig {
    @Bean
    public DatabaseIdProvider getDatabaseIdProvider(){
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.setProperty("Oracle1","oracle");
        properties.setProperty("MySQL","mysql");
        properties.setProperty("SqlServer","sqlserver");
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }
}
