package com.lomeone.eunoia.aws.security.exception

import com.lomeone.eunoia.exception.EunioaException
import com.lomeone.eunoia.exception.ExceptionDetail

private const val MESSAGE = "Key unable to generate."

class KeyUnableGenerateException(
    message: String = MESSAGE,
    details: Map<String, Any>
) : EunioaException(
    message = message,
    errorCode = KMSErrorCode.UNABLE_GENERATE_KEY,
    detail = ExceptionDetail(details)
)
