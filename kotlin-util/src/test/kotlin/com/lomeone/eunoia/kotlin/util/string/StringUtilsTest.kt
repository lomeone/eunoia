package com.lomeone.eunoia.kotlin.util.string

import com.lomeone.eunoia.kotlin.util.string.StringUtils.generateRandomString
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe

class StringUtilsTest : FreeSpec({
    "원하는 charter set으로 원하는 길이의 랜덤 문자열을 생성할 수 있다" - {
        val charSet = setOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', ' ', '1', '!', '@')
        val length = 10
        val randomString = generateRandomString(charSet, length)

        println(randomString)

        "랜덤 문자열의 길이는 $length 이어야 한다" {
            randomString.length shouldBe length
        }
        "랜덤 문자열은 $charSet 의 문자로만 이루어져야 한다" {
            randomString.forEach { it shouldBeIn charSet }
        }
    }
})
