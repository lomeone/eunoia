package com.lomeone.eunoia.aws.security.exception

import com.lomeone.eunoia.exception.ErrorCode
import com.lomeone.eunoia.exception.ExceptionCategory

object KMSErrorCode {
    const val ERROR_CODE_PREFIX = "kms"
    val UNABLE_GENERATE_KEY = ErrorCode(
        code = "${ERROR_CODE_PREFIX}/unable-generate-key",
        exceptionCategory = ExceptionCategory.INTERNAL_SERVER_ERROR
    )
    val UNABLE_DECRYPT_DATA_KEY = ErrorCode(
        code = "${ERROR_CODE_PREFIX}/unable-decrypt-data-key",
        exceptionCategory = ExceptionCategory.INTERNAL_SERVER_ERROR
    )
}
