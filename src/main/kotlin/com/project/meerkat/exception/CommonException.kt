package com.project.meerkat.exception

import org.springframework.http.HttpStatus

data class CommonException(
    val errorCode: ErrorCode,
    override val message: String,
    val httpStatus: HttpStatus
): RuntimeException(message)

