package com.lomeone.eunoia.kotlin.util.security.authentication

import com.lomeone.eunoia.kotlin.util.security.authentication.exception.PasswordInvalidException
import com.lomeone.eunoia.kotlin.util.security.authentication.exception.PasswordIsBlankException

object PasswordUtils {
    fun checkPasswordValidity(password: String) {
        checkPasswordIsNotBlank(password)
        checkPasswordFormatValid(password)
    }

    private fun checkPasswordIsNotBlank(password: String) {
        password.isBlank() && throw PasswordIsBlankException(mapOf("password" to password))
    }

    private fun checkPasswordFormatValid(password: String) {
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\$@!%*?&])[A-Za-z\\d\$@!%*?&]{10,}")
        !regex.matches(password) && throw PasswordInvalidException(mapOf("password" to password))
    }
}
