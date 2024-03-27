package com.project.meerkat.config

import org.modelmapper.Converter
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ApplicationConfig {
    @Bean
    fun modelMapper(): ModelMapper {
        val modelMapper = ModelMapper()
        val toStringInteger: Converter<String?, Int?> = Converter<String?, Int?> { context -> context.source.toInt() }
        val toIntegerString: Converter<Int?, String?> = Converter<Int?, String?> { context -> context.source.toString() }
        val toStringLong: Converter<String?, Long?> = Converter<String?, Long?> { context -> context.source.toLong() }
        val toLongString: Converter<Long?, String?> = Converter<Long?, String?> { context -> context.source.toString() }

        modelMapper.addConverter(toStringInteger)
        modelMapper.addConverter(toIntegerString)
        modelMapper.addConverter(toStringLong)
        modelMapper.addConverter(toLongString)
        modelMapper.configuration.setSkipNullEnabled(true)
        modelMapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        modelMapper.configuration.isFieldMatchingEnabled = true
        modelMapper.configuration.fieldAccessLevel = org.modelmapper.config.Configuration.AccessLevel.PRIVATE

        return modelMapper
    }
}
