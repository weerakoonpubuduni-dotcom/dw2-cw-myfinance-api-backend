package com.my_finance.my_finance.Config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class OracleConfig {
    @Bean
    @ConfigurationProperties("oracle.datasource")
    public DataSourceProperties oracleDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "oracleDataSource")
    public DataSource oracleDataSource(DataSourceProperties props) {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(props.getDriverClassName());
        ds.setUrl(props.getUrl());
        ds.setUsername(props.getUsername());
        ds.setPassword(props.getPassword());
        return ds;
    }

}
