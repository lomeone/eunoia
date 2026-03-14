package com.lomeone.eunoia.security.crypto.symmetric

import com.lomeone.eunoia.security.crypto.Crypto
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class AESGCMCrypto(
    private val key: ByteArray
) : Crypto {
    companion object {
        private const val ALGORITHM: String = "AES/GCM/NoPadding"
        private const val IV_SIZE = 12
        private const val TAG_SIZE = 128
    }

    init {
        require(key.size in setOf(16, 24, 32)) {
            "Invalid AES key size. Must be 16, 24 or 32 bytes long."
        }
    }

    private val secureRandom = SecureRandom()

    override suspend fun encrypt(data: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(ALGORITHM)
        val iv = generateRandomIV()
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(this.key, "AES"), GCMParameterSpec(TAG_SIZE, iv))

        return iv + cipher.doFinal(data)
    }

    private fun generateRandomIV(): ByteArray {
        val iv = ByteArray(IV_SIZE)
        this.secureRandom.nextBytes(iv)
        return iv
    }

    override suspend fun decrypt(encryptedData: ByteArray): ByteArray {
        val (iv, encryptedData) = separateIVAndEncryptedData(encryptedData)

        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(this.key, "AES"), GCMParameterSpec(TAG_SIZE, iv))

        return cipher.doFinal(encryptedData)
    }

    private fun separateIVAndEncryptedData(encryptedData: ByteArray): Pair<ByteArray, ByteArray> =
        Pair(encryptedData.copyOfRange(0, IV_SIZE), encryptedData.copyOfRange(IV_SIZE, encryptedData.size))
}
