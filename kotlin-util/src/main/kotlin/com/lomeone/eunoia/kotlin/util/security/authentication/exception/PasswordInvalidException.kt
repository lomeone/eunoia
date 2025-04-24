package com.lomeone.eunoia.kotlin.util.security.authentication.exception

import com.lomeone.eunoia.exception.ErrorCode
import com.lomeone.eunoia.exception.EunioaException
import com.lomeone.eunoia.exception.ExceptionCategory
import com.lomeone.eunoia.exception.ExceptionDetail

private const val MESSAGE = "Password is invalid. Password must be at least 10 characters, including at least one uppercase letter, one lowercase letter, one number and one special character."
private val ERROR_CODE = ErrorCode(
    code = "password/invalid",
    ExceptionCategory.BAD_REQUEST
)

class PasswordInvalidException(
    details: Map<String, Any>
) : EunioaException(
    message = MESSAGE,
    errorCode = ERROR_CODE,
    detail = ExceptionDetail(details)
)
