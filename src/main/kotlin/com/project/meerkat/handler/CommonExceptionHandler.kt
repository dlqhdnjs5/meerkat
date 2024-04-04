package com.project.meerkat.handler

import com.project.meerkat.exception.CommonException
import com.project.meerkat.exception.ErrorCode
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@Slf4j
@RestControllerAdvice
class CommonExceptionHandler {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(CommonExceptionHandler::class.java)
    }

    @ExceptionHandler(CommonException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun commonExceptionHandler(commonException: CommonException): ResponseEntity<ErrorResponse> {
        logger.error(commonException.message, commonException)
        return ResponseEntity(ErrorResponse(commonException.errorCode, commonException.errorCode.message), commonException.httpStatus)
    }

    data class ErrorResponse(val errorCode: ErrorCode, val message: String)
}