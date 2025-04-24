package com.lomeone.eunoia.kotlin.util.string

object StringUtils {
    fun generateRandomString(charSet: Set<Char>, length: Int): String =
        (1..length).map { charSet.random() }
            .joinToString("")
}
