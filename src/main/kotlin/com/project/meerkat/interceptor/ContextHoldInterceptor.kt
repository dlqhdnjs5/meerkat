package com.project.meerkat.interceptor

import com.project.meerkat.common.model.UserInfo
import com.project.meerkat.common.util.ContextHolderUtil
import com.project.meerkat.common.service.JwtService
import com.project.meerkat.service.member.MemberSerivce
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Configuration
import org.springframework.util.ObjectUtils
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class ContextHoldInterceptor(
    private val memberService: MemberSerivce,
    private val jwtService: JwtService,
    private val modelMapper: ModelMapper
): HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token = jwtService.resolveJwt(request)

        if (token != null && jwtService.validateToken(token)) {
            val email = jwtService.getClaimSubjectFromJwt(token)

            if (email != null) {
                val memberEntity = memberService.getMemberByEmail(email)

                if (ObjectUtils.isEmpty(memberEntity)) {
                    super.preHandle(request, response, handler)
                }

                ContextHolderUtil.putUserInfo(modelMapper.map(memberEntity, UserInfo::class.java))
            }
        }
        return super.preHandle(request, response, handler)
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        exception: Exception?
    ) {
        ContextHolderUtil.clearUserInfo()
        super.afterCompletion(request, response, handler, exception)
    }

}