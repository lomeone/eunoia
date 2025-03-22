package com.lomeone.eunoia.exception

open class EunioaException(
    message: String,
    val errorCode: ErrorCode,
    val detail: ExceptionDetail,
    cause: Throwable? = null
) : RuntimeException(message, cause)

data class ErrorCode(
    val code: String,
    val exceptionCategory: ExceptionCategory
)

enum class ExceptionCategory {
    // Client errors
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,

    // Server errors
    INTERNAL_SERVER_ERROR,
    SERVICE_UNAVAILABLE
}

data class ExceptionDetail(
    val details: Map<String, Any>
)
