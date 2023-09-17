package com.typeface.assignment.filestorageservice.infrastructure.external.mysql.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.typeface.assignment.filestorageservice.infrastructure.repositories",
        entityManagerFactoryRef = "entityManager",
        transactionManagerRef = "transactionManager"
)
@EnableTransactionManagement
public class MysqlDataSourceConfiguration {

    private final DataSourceConfigurationProperties properties;


    public MysqlDataSourceConfiguration(DataSourceConfigurationProperties properties) {
        this.properties = properties;
    }

    @Bean
    @Primary
    public DataSource primaryDataSourceConfig(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getUrl());
        hikariConfig.setUsername(properties.getUsername());
        hikariConfig.setPassword(properties.getPassword());
        hikariConfig.setDriverClassName(properties.getDriverClassName());
        hikariConfig.setConnectionTimeout(properties.getConnectionTimeout());
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManager(){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(primaryDataSourceConfig());
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.typeface");
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("primary");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }


}
