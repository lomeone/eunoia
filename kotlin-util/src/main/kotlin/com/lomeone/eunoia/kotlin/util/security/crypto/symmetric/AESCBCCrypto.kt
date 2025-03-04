package com.lomeone.eunoia.kotlin.util.security.crypto.symmetric

import com.lomeone.eunoia.kotlin.util.security.crypto.Crypto
import com.lomeone.eunoia.kotlin.util.security.crypto.getKeySpec
import java.util.*
import javax.crypto.Cipher

object AESCBCCrypto : Crypto {
    private const val ALGORITHM: String = "AES/CBC/PKCS5Padding"
    private const val IV_SIZE = 16

    override fun encrypt(plainText: String, key: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val iv = generateRandomIV(IV_SIZE)
        cipher.init(Cipher.ENCRYPT_MODE, getKeySpec(key, "AES"), iv)
        return Base64.getEncoder().encodeToString(iv.iv + cipher.doFinal(plainText.toByteArray()))
    }

    override fun decrypt(cipherText: String, key: String): String {
        val (iv, encryptedData) = separateIVAndCipherText(cipherText, IV_SIZE)

        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, getKeySpec(key, "AES"), iv)

        return String(cipher.doFinal(encryptedData))
    }
}
