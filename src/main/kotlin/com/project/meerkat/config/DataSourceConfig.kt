package com.project.meerkat.config

import com.project.meerkat.common.GlobalPropertySource
import com.zaxxer.hikari.HikariDataSource
import lombok.RequiredArgsConstructor
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
}