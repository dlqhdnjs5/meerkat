package com.project.meerkat.config

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ApplicationConfig {
    @Bean
    fun modelMapper(): ModelMapper {
        val modelMapper = ModelMapper()
        modelMapper.configuration.setSkipNullEnabled(true)
        modelMapper.configuration.matchingStrategy = MatchingStrategies.STRICT

        return modelMapper
    }
}
