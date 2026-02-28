package com.lomeone.eunoia.aws.security

import aws.sdk.kotlin.services.kms.KmsClient
import aws.sdk.kotlin.services.kms.decrypt
import aws.sdk.kotlin.services.kms.generateDataKey
import aws.sdk.kotlin.services.kms.model.DataKeySpec
import com.lomeone.eunoia.security.crypto.Crypto
import com.lomeone.eunoia.security.crypto.symmetric.AESGCMCrypto
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class KMSEnvelopeCrypto(
    private val kmsClient: KmsClient,
    private val keyArn: String
) : Crypto {
    data class KMSKeys(
        val plainKey: ByteArray,
        val encryptedKey: ByteArray
    )

    private var cachedKeys: KMSKeys? = null
    private val mutex = Mutex()

    private suspend fun getKeys(): KMSKeys {
        cachedKeys?.let { return it }

        return mutex.withLock {
            cachedKeys ?: run {
                val response = kmsClient.generateDataKey {
                    keyId = keyArn
                    keySpec = DataKeySpec.Aes256
                }

                KMSKeys(
                    plainKey = response.plaintext ?: throw IllegalStateException("Unable to generate dataKey"),
                    encryptedKey = response.ciphertextBlob ?: throw IllegalStateException("Unable to generate encryptedDataKey")
                ).also { cachedKeys = it }
            }
        }
    }

    override suspend fun encrypt(data: ByteArray): ByteArray {
        val (dataKey, encryptedDataKey) = getKeys()
        return packageInEnvelope(
            encryptedDataKey = encryptedDataKey,
            encryptedData = AESGCMCrypto(dataKey).encrypt(data)
        )
    }

    private fun packageInEnvelope(encryptedDataKey: ByteArray, encryptedData: ByteArray): ByteArray {
        val envelope = ByteArray(2 + encryptedDataKey.size + encryptedData.size)

        // https://docs.aws.amazon.com/encryption-sdk/latest/developer-guide/message-format.html#data-key-length
        // EncryptedDataKey의 길이는 2Byte로 표현
        envelope[0] = (encryptedDataKey.size shr 8).toByte()
        envelope[1] = (encryptedDataKey.size and 0xFF).toByte()
        encryptedDataKey.copyInto(destination = envelope, destinationOffset = 2)
        encryptedData.copyInto(destination = envelope, destinationOffset = 2 + encryptedDataKey.size)

        return envelope
    }

    override suspend fun decrypt(encryptedData: ByteArray): ByteArray {
        val (encryptedDataKey, ciphertextBytes) = unpackageFromEnvelope(encryptedData)

        val dataKey = kmsClient.decrypt {
            ciphertextBlob = encryptedDataKey
            keyId = keyArn
        }.plaintext ?: throw IllegalStateException("Unable to decrypt dataKey")

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
