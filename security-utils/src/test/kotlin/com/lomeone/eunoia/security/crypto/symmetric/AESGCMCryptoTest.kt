package com.lomeone.eunoia.security.crypto.symmetric

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class AESGCMCryptoTest : FreeSpec({
    val key = "1234567890abcdef".toByteArray()
    val aesGcmCrypto = AESGCMCrypto(key)
    "암호화할 수 있다" - {
        val plainData = "HelloWorld!".toByteArray()
        val encryptedData = aesGcmCrypto.encrypt(plainData)

        encryptedData shouldNotBe plainData

        println("encryptedData: ${String(encryptedData)}, plainData: ${String(plainData)}")

        "암호문을 복호화할 수 있다" - {
            val decryptedData = aesGcmCrypto.decrypt(encryptedData)

            decryptedData shouldNotBe encryptedData
            decryptedData shouldBe plainData

            println("decryptedData: ${String(decryptedData)}")
        }
    }

})
