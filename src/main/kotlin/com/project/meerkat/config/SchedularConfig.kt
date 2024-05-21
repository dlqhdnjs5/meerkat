package com.project.meerkat.config

import com.project.meerkat.exception.CommonException
import com.project.meerkat.exception.ErrorCode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.configuration.JobLocator
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@EnableScheduling
@Configuration
class SchedularConfig {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(SchedularConfig::class.java)
    }

    @Autowired
    private lateinit var jobLauncher: JobLauncher
    @Autowired
    lateinit var jobLocator: JobLocator
    @Scheduled(cron = "0 0 0/1 1/1 * ? *")
    fun runSyncApartmentRentJob() {
         runJob("syncApartmentRentJob")
         runJob("syncApartmentTradeJob")
    }

    private fun runJob(jobName: String) {
        try {
            val jobParameters = JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters()

            val job = jobLocator.getJob(jobName)
            jobLauncher.run(job, jobParameters)
        } catch (exception: Exception) {
            logger.error("Error executing job: ${exception.message}", exception)
            throw CommonException(ErrorCode.BATCH_INTERNAL_SERVER_EXECEOTION, "Exception occured while run ${jobName}", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}