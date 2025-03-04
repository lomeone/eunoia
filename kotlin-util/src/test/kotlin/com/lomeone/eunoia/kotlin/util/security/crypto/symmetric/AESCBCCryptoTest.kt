package com.lomeone.eunoia.kotlin.util.security.crypto.symmetric

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class AESCBCCryptoTest : FreeSpec({
    "문자열을 암호화하고 복호화할 수 있다" - {
        val encrypted = AESCBCCrypto.encrypt("test", "1234567890abcdef")
        val decrypted = AESCBCCrypto.decrypt(encrypted, "1234567890abcdef")

        println("encrypted: $encrypted")
        println("decrypted: $decrypted")

        encrypted shouldNotBe "test"
        decrypted shouldBe "test"
    }
})
