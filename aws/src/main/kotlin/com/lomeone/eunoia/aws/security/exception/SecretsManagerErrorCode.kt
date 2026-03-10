package com.lomeone.eunoia.aws.security.exception

import com.lomeone.eunoia.exception.ErrorCode
import com.lomeone.eunoia.exception.ExceptionCategory

object SecretsManagerErrorCode {
    const val ERROR_CODE_PREFIX = "secretsmanager"
    val SECRET_NOT_FOUND = ErrorCode(
        code = "${ERROR_CODE_PREFIX}/secret-not-found",
        exceptionCategory = ExceptionCategory.NOT_FOUND
    )
}
