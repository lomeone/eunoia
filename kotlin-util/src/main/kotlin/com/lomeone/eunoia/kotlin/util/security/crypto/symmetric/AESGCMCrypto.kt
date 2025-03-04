package com.lomeone.eunoia.kotlin.util.security.crypto.symmetric

import com.lomeone.eunoia.kotlin.util.security.crypto.Crypto
import com.lomeone.eunoia.kotlin.util.security.crypto.getKeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec

object AESGCMCrypto : Crypto {
    private const val ALGORITHM: String = "AES/GCM/NoPadding"
    private const val IV_SIZE = 12
    private const val TAG_SIZE = 128

    override fun encrypt(plainText: String, key: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val iv = generateRandomIV(IV_SIZE)
        cipher.init(Cipher.ENCRYPT_MODE, getKeySpec(key, "AES"), GCMParameterSpec(TAG_SIZE, iv.iv))

        return Base64.getEncoder().encodeToString(iv.iv + cipher.doFinal(plainText.toByteArray()))
    }

    override fun decrypt(cipherText: String, key: String): String {
        val (iv, encryptedData) = separateIVAndCipherText(cipherText, IV_SIZE)

        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, getKeySpec(key, "AES"), GCMParameterSpec(TAG_SIZE, iv.iv))

        return String(cipher.doFinal(encryptedData))
    }
}
