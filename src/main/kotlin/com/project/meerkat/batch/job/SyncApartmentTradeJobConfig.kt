package com.project.meerkat.batch.job

import com.project.meerkat.model.apartment.entity.ApartmentRentEntity
import com.project.meerkat.model.apartment.entity.ApartmentTradeEntity
import com.project.meerkat.service.apartment.ApartmentService
import com.project.meerkat.service.notification.NotificationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class SyncApartmentTradeJobConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val notificationService: NotificationService,
    val apartmentService: ApartmentService
) {
    var readCount = 0
    var writeCount = 0
    var batchExecutionTime: LocalDateTime = LocalDateTime.now()
    var beforeOneDayAsYearAndDay: String = ""
    var beforeOneDay: String = ""
    var sigunguCodes: MutableList<String> = mutableListOf()
    companion object {
        val logger: Logger = LoggerFactory.getLogger(SyncApartmentTradeJobConfig::class.java)
    }

    @Bean
    fun syncApartmentTradeJob(): Job {
        return jobBuilderFactory.get("syncApartmentTradeJob")
            .incrementer(RunIdIncrementer())
            .start(syncApartmentTradeStep())
            .listener(syncApartmentTradeJobListener())
            .build()
    }

    @Bean
    fun syncApartmentTradeJobListener(): JobExecutionListener {
        return object : JobExecutionListener {
            override fun beforeJob(jobExecution: JobExecution) {
                beforeOneDayAsYearAndDay = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMM"))
                beforeOneDay = LocalDate.now().minusDays(1).dayOfMonth.toString()
                batchExecutionTime = LocalDateTime.now()
            }

            override fun afterJob(jobExecution: JobExecution) {
                sigunguCodes.clear()
                readCount = 0
                writeCount = 0
            }
        }
    }

    @Bean
    @JobScope
    fun syncApartmentTradeStep(): Step {
        return stepBuilderFactory.get("syncApartmentTradeStep")
            .listener(syncApartmentTradeStepListener())
            .chunk<ApartmentTradeEntity, ApartmentTradeEntity>(50)
            .reader(syncApartmentTradeReader())
            .processor(syncApartmentTradeProcessor())
            .writer(syncApartmentTradeWriter())
            .build()
    }

    @Bean
    fun syncApartmentTradeStepListener(): StepExecutionListener {
        return object : StepExecutionListener {
            override fun beforeStep(stepExecution: StepExecution) {
                sigunguCodes.addAll(notificationService.getAllSigunguCodes())
            }

            override fun afterStep(stepExecution: StepExecution): ExitStatus {
                if (stepExecution.status == BatchStatus.COMPLETED) {
                    readCount = stepExecution.readCount
                    writeCount = stepExecution.writeCount

                    logger.info("dateTime: ${batchExecutionTime}, syncApartmentTrade readCount: ${readCount}, writeCount: ${writeCount}")
                }

                return stepExecution.exitStatus
            }
        }
    }

    @Bean
    @StepScope
    fun syncApartmentTradeReader(): ListItemReader<ApartmentTradeEntity> {
        if (sigunguCodes.isEmpty()) {
            return ListItemReader(emptyList())
        }

        return ListItemReader(apartmentService.getApartmentTrades(sigunguCodes, beforeOneDayAsYearAndDay))
    }

    @Bean
    @StepScope
    fun syncApartmentTradeProcessor(): ItemProcessor<ApartmentTradeEntity, ApartmentTradeEntity> {
        return ItemProcessor {
            if (it.day != beforeOneDay) {
                return@ItemProcessor null
            }

            logger.info(it.toString())
            it.apply { it.migDate = batchExecutionTime }
        }
    }

    @Bean
    @StepScope
    fun syncApartmentTradeWriter(): ItemWriter<ApartmentTradeEntity> {
        return ItemWriter { items -> apartmentService.addApartmentTrades(items) }
    }
}