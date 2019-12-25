package com.xyyh.rds.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xyyh.rds.datasource.RequestRoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource() {
        DataSource readSource = readSource();
        DataSource writeSource = writeSource();
        RequestRoutingDataSource source = new RequestRoutingDataSource();
        Map<Object, Object> sourceMap = new HashMap<>();
        sourceMap.put("read", readSource);
        sourceMap.put("write", writeSource);
        source.setTargetDataSources(sourceMap);
        source.setDefaultTargetDataSource(readSource);
        return source;
    }

    private DataSource readSource() {
        HikariDataSource readDataSource = new HikariDataSource();
        readDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        readDataSource.setJdbcUrl(
                "jdbc:mysql://localhost/d1?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&serverTimezone=Asia/Shanghai");
        readDataSource.setUsername("root");
        readDataSource.setPassword("admin123456!@#");
        return readDataSource;
    }

    private DataSource writeSource() {
        HikariDataSource writeSource = new HikariDataSource();
        writeSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        writeSource.setJdbcUrl(
                "jdbc:mysql://localhost/d2?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&serverTimezone=Asia/Shanghai");
        writeSource.setUsername("root");
        writeSource.setPassword("admin123456!@#");
        return writeSource;
    }

}
