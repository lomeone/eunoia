package com.lomeone.eunoia.aws.security

import aws.sdk.kotlin.services.kms.KmsClient
import aws.sdk.kotlin.services.kms.decrypt
import aws.sdk.kotlin.services.kms.generateDataKey
import aws.sdk.kotlin.services.kms.model.DataKeySpec
import com.lomeone.eunoia.security.crypto.Crypto
import com.lomeone.eunoia.security.crypto.symmetric.AESGCMCrypto
import kotlinx.coroutines.runBlocking

class KMSEnvelopeCrypto(
    private val kmsClient: KmsClient,
    private val keyArn: String
) : Crypto {
    val dataKey: ByteArray
    val encryptedDataKey: ByteArray

    init {
        val generateDataKeyResponse = runBlocking {
            kmsClient.generateDataKey {
                keyId = keyArn
                keySpec = DataKeySpec.Aes256
            }
        }

        dataKey = generateDataKeyResponse.plaintext ?: throw IllegalStateException("Unable to generate dataKey")
        encryptedDataKey = generateDataKeyResponse.ciphertextBlob ?: throw IllegalStateException("Unable to generate encryptedDataKey")
    }

    override fun encrypt(data: ByteArray): ByteArray {
        return packageInEnvelope(AESGCMCrypto(dataKey).encrypt(data))
    }

    private fun packageInEnvelope(encryptedData: ByteArray): ByteArray {
        val envelope = ByteArray(2 + encryptedDataKey.size + encryptedData.size)

        // https://docs.aws.amazon.com/encryption-sdk/latest/developer-guide/message-format.html#data-key-length
        // EncryptedDataKey의 길이는 2Byte로 표현
        envelope[0] = (encryptedDataKey.size shr 8).toByte()
        envelope[1] = (encryptedDataKey.size and 0xFF).toByte()
        encryptedDataKey.copyInto(destination = envelope, destinationOffset = 2)
        encryptedData.copyInto(destination = envelope, destinationOffset = 2 + encryptedDataKey.size)

        return envelope
    }

    override fun decrypt(encryptedData: ByteArray): ByteArray {
        val (encryptedDataKey, ciphertextBytes) = unpackageFromEnvelope(encryptedData)

        val dataKey = runBlocking {
            kmsClient.decrypt {
                ciphertextBlob = encryptedDataKey
                keyId = keyArn
            }
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
