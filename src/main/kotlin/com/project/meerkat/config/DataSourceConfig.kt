package com.project.meerkat.config

import com.project.meerkat.common.GlobalPropertySource
import com.zaxxer.hikari.HikariDataSource
import lombok.RequiredArgsConstructor
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import java.util.*
import javax.sql.DataSource


@Configuration
@RequiredArgsConstructor
class DataSourceConfig(
    private val globalPropertySource: GlobalPropertySource
) {
    @Bean
    fun meerkatDatasource(): DataSource {
        return DataSourceBuilder
            .create()
            .type(HikariDataSource::class.java)
            .driverClassName(globalPropertySource.driverClassName)
            .url(globalPropertySource.url)
            .username(globalPropertySource.username)
            .password(globalPropertySource.password)
            .build()
    }

    private fun additionalProperties(): Properties {
        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", "update")
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
        properties.setProperty("hibernate.show_sql", "true")
        properties.setProperty("hibernate.format_sql", "true")
        properties.setProperty("hibernate.use_sql_comments", "true")
        return properties
    }

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        entityManagerFactoryBean.dataSource = meerkatDatasource()
        entityManagerFactoryBean.setPackagesToScan(*arrayOf("com.project.meerkat.model/**/**"))
        entityManagerFactoryBean.persistenceUnitName = "meerkatPersist"

        val vendorAdapter: JpaVendorAdapter = HibernateJpaVendorAdapter()
        entityManagerFactoryBean.jpaVendorAdapter = vendorAdapter
        entityManagerFactoryBean.setJpaProperties(additionalProperties())

        return entityManagerFactoryBean
    }

    @Bean
    fun transactionManager(): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory().getObject()

        return transactionManager
    }

    @Bean
    fun exceptionTranslation(): PersistenceExceptionTranslationPostProcessor{
        return PersistenceExceptionTranslationPostProcessor();
    }
}