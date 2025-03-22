package com.lomeone.eunoia.spring.web.rest

import com.lomeone.eunoia.exception.EunioaException
import com.lomeone.eunoia.exception.ExceptionCategory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.ZonedDateTime

@RestControllerAdvice
class EunoiaExceptionControlAdvice {
    @ExceptionHandler(EunioaException::class)
    fun handler(e: EunioaException): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(
            status = convertExceptionCategoryToHttpStatusCode(e.errorCode.exceptionCategory).value(),
            errorCode = e.errorCode.code,
            message = e.message,
            detail = e.detail.details
        )

        return ResponseEntity.status(convertExceptionCategoryToHttpStatusCode(e.errorCode.exceptionCategory)).body(response)
    }

    private fun convertExceptionCategoryToHttpStatusCode(exceptionCategory: ExceptionCategory): HttpStatus =
        when(exceptionCategory) {
            ExceptionCategory.BAD_REQUEST -> HttpStatus.BAD_REQUEST
            ExceptionCategory.UNAUTHORIZED -> HttpStatus.UNAUTHORIZED
            ExceptionCategory.FORBIDDEN -> HttpStatus.FORBIDDEN
            ExceptionCategory.NOT_FOUND -> HttpStatus.NOT_FOUND
            ExceptionCategory.INTERNAL_SERVER_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR
            ExceptionCategory.SERVICE_UNAVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE
        }
}

data class ExceptionResponse(
    val timestamp: ZonedDateTime = ZonedDateTime.now(),
    val status: Int,
    val errorCode: String,
    val message: String? = null,
    val detail: Map<String, Any> = mapOf()
)
