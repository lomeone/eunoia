package org.example.com.eunoia.exception

open class EunioaException(
    message: String,
    val errorCode: ErrorCode,
    val detail: Map<String, Any> = mapOf(),
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
