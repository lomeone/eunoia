package com.lomeone.eunoia.kotlin.util.security.crypto

import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

object AESCrypto : Crypto {
    private const val ALGORITHM: String = "AES/CBC/PKCS5Padding"
    private const val IV_SIZE = 16

    override fun encrypt(plainText: String, key: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val iv = generateRandomIV()
        cipher.init(Cipher.ENCRYPT_MODE, getKeySpec(key, "AES"), iv)
        return Base64.getEncoder().encodeToString(iv.iv + cipher.doFinal(plainText.toByteArray()))
    }

    private fun generateRandomIV(): IvParameterSpec {
        val iv = ByteArray(IV_SIZE)
        SecureRandom().nextBytes(iv)
        return IvParameterSpec(iv)
    }

    override fun decrypt(cipherText: String, key: String): String {
        val decodedData = Base64.getDecoder().decode(cipherText)

        val iv = IvParameterSpec(decodedData.copyOfRange(0, IV_SIZE))
        val encryptedData = decodedData.copyOfRange(IV_SIZE, decodedData.size)

        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, getKeySpec(key, "AES"), iv)

        return String(cipher.doFinal(encryptedData))
    }
}
