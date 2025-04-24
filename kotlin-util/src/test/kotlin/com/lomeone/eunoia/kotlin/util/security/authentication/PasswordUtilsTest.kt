package com.lomeone.eunoia.kotlin.util.security.authentication

import com.lomeone.eunoia.kotlin.util.security.authentication.PasswordUtils.checkPasswordValidity
import com.lomeone.eunoia.kotlin.util.security.authentication.exception.PasswordInvalidException
import com.lomeone.eunoia.kotlin.util.security.authentication.exception.PasswordIsBlankException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec

class PasswordUtilsTest : FreeSpec({
    "비밀번호 형식이 맞는지 검증할 수 있다" - {
        shouldThrow<PasswordIsBlankException> {
            checkPasswordValidity("")
        }

        shouldThrow<PasswordInvalidException> {
            checkPasswordValidity("abcd1234")
        }

        checkPasswordValidity("testPassword1324@")
    }
})
