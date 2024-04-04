package com.project.meerkat.exception

enum class ErrorCode(val message: String) {
    BAD_REQUEST("Bad requested"),
    NOT_FOUND("Requested resource not found"),
    NOT_ALLOWED("Operation not allowed"),
    AUTHENTICATION_REQUIRED("Authentication must be required")
}