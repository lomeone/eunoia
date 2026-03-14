package com.lomeone.eunoia.aws.security

import aws.sdk.kotlin.services.kms.KmsClient
import aws.sdk.kotlin.services.kms.decrypt
import aws.sdk.kotlin.services.kms.generateDataKey
import aws.sdk.kotlin.services.kms.model.DataKeySpec
import com.lomeone.eunoia.aws.security.exception.DataKeyUnableDecryptException
import com.lomeone.eunoia.aws.security.exception.KeyUnableGenerateException
import com.lomeone.eunoia.security.crypto.Crypto
import com.lomeone.eunoia.security.crypto.symmetric.AESGCMCrypto
import kotlinx.coroutines.runBlocking

class KMSEnvelopeCrypto(
    private val kmsClient: KmsClient,
    private val keyArn: String
) : Crypto {
    data class KMSKeys(
        val plainKey: ByteArray,
        val encryptedKey: ByteArray
    )

    private val kmsKey: KMSKeys by lazy {
        runBlocking {
            val response = kmsClient.generateDataKey {
                keyId = keyArn
                keySpec = DataKeySpec.Aes256
            }

            KMSKeys(
                plainKey = response.plaintext
                    ?: throw KeyUnableGenerateException(
                        message = "Unable to generate dataKey",
                        details = mapOf("keyArn" to keyArn)
                    ),
                encryptedKey = response.ciphertextBlob
                    ?: throw KeyUnableGenerateException(
                        message = "Unable to generate encryptedDataKey",
                        details = mapOf("keyArn" to keyArn)
                    )
            )
        }
    }

    override suspend fun encrypt(data: ByteArray): ByteArray {
        return packageInEnvelope(AESGCMCrypto(kmsKey.plainKey).encrypt(data))
    }

    private fun packageInEnvelope(encryptedData: ByteArray): ByteArray {
        val envelope = ByteArray(2 + kmsKey.encryptedKey.size + encryptedData.size)

        // https://docs.aws.amazon.com/encryption-sdk/latest/developer-guide/message-format.html#data-key-length
        // EncryptedDataKey의 길이는 2Byte로 표현
        envelope[0] = (kmsKey.encryptedKey.size shr 8).toByte()
        envelope[1] = (kmsKey.encryptedKey.size and 0xFF).toByte()
        kmsKey.encryptedKey.copyInto(destination = envelope, destinationOffset = 2)
        encryptedData.copyInto(destination = envelope, destinationOffset = 2 + kmsKey.encryptedKey.size)

        return envelope
    }

    override suspend fun decrypt(encryptedData: ByteArray): ByteArray {
        val (encryptedDataKey, ciphertextBytes) = unpackageFromEnvelope(encryptedData)

        val dataKey = kmsClient.decrypt {
            ciphertextBlob = encryptedDataKey
            keyId = keyArn
        }.plaintext ?: throw DataKeyUnableDecryptException(
            mapOf(
                "keyArn" to keyArn,
                "encryptedDataKey" to encryptedDataKey.decodeToString().replace("\n", "")
            )
        )

        return AESGCMCrypto(dataKey).decrypt(ciphertextBytes)
    }

    private fun unpackageFromEnvelope(packagedData: ByteArray): Pair<ByteArray, ByteArray> {
        // KeySize 복원
        val keySize = ((packagedData[0].toInt() and 0xFF) shl 8) or (packagedData[1].toInt() and 0xFF)

        val encryptedDataKey = packagedData.copyOfRange(2, 2 + keySize)

        val encryptedData = packagedData.copyOfRange(2 + keySize, packagedData.size)

        return Pair(encryptedDataKey, encryptedData)
    }
}
