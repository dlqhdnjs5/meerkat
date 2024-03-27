package com.project.meerkat.config

import com.project.meerkat.common.service.JwtService
import com.project.meerkat.interceptor.ContextHoldInterceptor
import com.project.meerkat.service.member.MemberSerivce
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val memberSerivce: MemberSerivce,
    private val jwtService: JwtService,
    private val modelMapper: ModelMapper,
): WebMvcConfigurer {
    companion object {
        private const val PATTERN_ALL = "/**"
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(ContextHoldInterceptor(memberSerivce, jwtService, modelMapper)).addPathPatterns(PATTERN_ALL)
        super.addInterceptors(registry)
    }
}