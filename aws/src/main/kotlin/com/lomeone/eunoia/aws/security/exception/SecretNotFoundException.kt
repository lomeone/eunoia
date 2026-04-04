package com.lomeone.eunoia.aws.security.exception

import com.lomeone.eunoia.exception.EunioaException
import com.lomeone.eunoia.exception.ExceptionDetail

private const val MESSAGE = "Secret not found."

class SecretNotFoundException(
    details: Map<String, Any>
) : EunioaException(
    message = MESSAGE,
    errorCode = SecretsManagerErrorCode.SECRET_NOT_FOUND,
    detail = ExceptionDetail(details)
)
