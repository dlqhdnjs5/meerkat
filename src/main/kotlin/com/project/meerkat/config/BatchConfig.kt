package com.project.meerkat.config

import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableBatchProcessing
class BatchConfig {
    @Bean
    fun jobRegistryBeanPostProcessor(jobRegistry: JobRegistry): JobRegistryBeanPostProcessor {
        val jobRegistryBeanPostProcessor = JobRegistryBeanPostProcessor()
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry)
        return jobRegistryBeanPostProcessor
    }
}