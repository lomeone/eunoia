package org.example.com.lomeone.eunoia.exception

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
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
}

data class ExceptionDetail(
    val details: Map<String, Any>
)
