package com.lomeone.eunoia.security.crypto.symmetric

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.byte
import io.kotest.property.arbitrary.byteArray
import io.kotest.property.arbitrary.constant
import io.kotest.property.arbitrary.element
import io.kotest.property.arbitrary.flatMap
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class AESGCMCryptoTest : FreeSpec({
    "16, 24, 32 바이트 키 길에 대해 모두 암복호화 테스트를 수행한다." - {
        val keySizeArb = Arb.element(16, 24, 32)
        val keyArb = keySizeArb.flatMap { size ->
            Arb.byteArray(Arb.constant(size), Arb.byte())
        }

        "데이터를 암호화하면 원본과 달라야하고 암호화된 데이터를 복호화하면 원본과 동일해야한다." - {
            "Bytes 데이터 테스트" - {
                val bytesDataArb = Arb.byteArray(Arb.int(1..1000), Arb.byte())
                checkAll(keyArb, bytesDataArb) { key, data ->
                    val aesGcmCrypto = AESGCMCrypto(key)

                    val encryptedData = aesGcmCrypto.encrypt(data)
                    encryptedData shouldNotBe data

                    val decryptedData = aesGcmCrypto.decrypt(encryptedData)
                    decryptedData shouldBe data
                }
            }

            "String 데이터 테스트" - {
                val stringDataArb = Arb.string(1..1000)
                checkAll(keyArb, stringDataArb) { key, data ->
                    val aesGcmCrypto = AESGCMCrypto(key)

                    val encryptedData = aesGcmCrypto.encrypt(data.toByteArray())
                    encryptedData shouldNotBe data.toByteArray()

                    val decryptedData = aesGcmCrypto.decrypt(encryptedData)
                    String(decryptedData) shouldBe data
                }
            }
        }
    }
})
