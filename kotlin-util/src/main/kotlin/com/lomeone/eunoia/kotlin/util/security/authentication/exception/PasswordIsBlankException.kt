package com.lomeone.eunoia.kotlin.util.security.authentication.exception

import com.lomeone.eunoia.exception.ErrorCode
import com.lomeone.eunoia.exception.EunioaException
import com.lomeone.eunoia.exception.ExceptionCategory
import com.lomeone.eunoia.exception.ExceptionDetail

private const val MESSAGE = "Password is blank. Password must not be blank."
private val ERROR_CODE = ErrorCode(
    code = "password/is-blank",
    ExceptionCategory.BAD_REQUEST
)

class PasswordIsBlankException(
    details: Map<String, Any>
) : EunioaException(
    message = MESSAGE,
    errorCode = ERROR_CODE,
    detail = ExceptionDetail(details)
)
