package com.lomeone.eunoia.aws.security.exception

import com.lomeone.eunoia.exception.EunioaException
import com.lomeone.eunoia.exception.ExceptionDetail

private const val MESSAGE = "Data key unable to decrypt."

class DataKeyUnableDecryptException(
    details: Map<String, Any>
) : EunioaException(
    message = MESSAGE,
    errorCode = KMSErrorCode.UNABLE_DECRYPT_DATA_KEY,
    detail = ExceptionDetail(details)
)
