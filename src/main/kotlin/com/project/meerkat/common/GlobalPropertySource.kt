package com.project.meerkat.common

import lombok.Getter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

@Configuration
@Getter
@PropertySources(
    PropertySource(
        value = arrayOf("file:/app/meerkat/config.properties"), // TODO prod 환경
        encoding = "utf-8",
        ignoreResourceNotFound = true
    ), PropertySource(value = arrayOf("/private.properties"), ignoreResourceNotFound = true)
)
class GlobalPropertySource {
    @Value("\${meerkat.datasource.driverClassName}")
    lateinit var driverClassName: String
    @Value("\${meerkat.datasource.url}")
    lateinit var url: String
    @Value("\${meerkat.datasource.username}")
    lateinit var username: String
    @Value("\${meerkat.datasource.password}")
    lateinit var password: String
    @Value("\${sec.web.token.key}")
    lateinit var secretWebTokenKey: String
    @Value("\${meerkat.web.auth.header}")
    lateinit var authHeader: String
}