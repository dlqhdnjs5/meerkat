package com.project.meerkat.batch.job

import com.project.meerkat.model.apartment.entity.ApartmentRentEntity
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
class SyncApartmentRentJobConfig(
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
        val logger: Logger = LoggerFactory.getLogger(SyncApartmentRentJobConfig::class.java)
    }

    @Bean
    fun syncApartmentRentJob(): Job {
        return jobBuilderFactory.get("syncApartmentRentJob")
            .incrementer(RunIdIncrementer())
            .start(syncApartmentRentStep())
            .listener(syncApartmentRentJobListener())
            .build()
    }

    @Bean
    fun syncApartmentRentJobListener(): JobExecutionListener {
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
    fun syncApartmentRentStep(): Step {
        return stepBuilderFactory.get("syncApartmentRentStep")
            .listener(syncApartmentRentStepListener())
            .chunk<ApartmentRentEntity, ApartmentRentEntity>(50)
            .reader(syncApartmentRentReader())
            .processor(syncApartmentRentProcessor())
            .writer(syncApartmentRentWriter())
            .build()
    }

    @Bean
    fun syncApartmentRentStepListener(): StepExecutionListener {
        return object : StepExecutionListener {
            override fun beforeStep(stepExecution: StepExecution) {
                sigunguCodes.addAll(notificationService.getAllSigunguCodes())
            }

            override fun afterStep(stepExecution: StepExecution): ExitStatus {
                if (stepExecution.status == BatchStatus.COMPLETED) {
                    readCount = stepExecution.readCount
                    writeCount = stepExecution.writeCount

                    logger.info("dateTime: ${batchExecutionTime}, syncApartmentRent readCount: ${readCount}, writeCount: ${writeCount}")
                }

                return stepExecution.exitStatus
            }
        }
    }

    @Bean
    @StepScope
    fun syncApartmentRentReader(): ListItemReader<ApartmentRentEntity> {
        if (sigunguCodes.isEmpty()) {
            return ListItemReader(emptyList())
        }

        return ListItemReader(apartmentService.getApartmentRents(sigunguCodes, beforeOneDayAsYearAndDay))
    }

    @Bean
    @StepScope
    fun syncApartmentRentProcessor(): ItemProcessor<ApartmentRentEntity, ApartmentRentEntity> {
        return ItemProcessor {
            if (it.dealDay != beforeOneDay) {
                return@ItemProcessor null
            }

            logger.info(it.toString())
            it.apply { it.migDate = batchExecutionTime }
        }
    }

    @Bean
    @StepScope
    fun syncApartmentRentWriter(): ItemWriter<ApartmentRentEntity> {
        return ItemWriter { items ->
            items.forEach {
                apartmentService.addApartmentRent(it)
            }
        }
    }
}