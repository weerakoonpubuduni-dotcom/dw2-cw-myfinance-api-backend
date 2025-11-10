package com.my_finance.my_finance.Config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class SqlLiteConfig {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties sqliteDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "sqliteDataSource")
    @Primary
    public DataSource sqliteDataSource(DataSourceProperties props) {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(props.getDriverClassName());
        ds.setUrl(props.getUrl());
        return ds;
    }

}
