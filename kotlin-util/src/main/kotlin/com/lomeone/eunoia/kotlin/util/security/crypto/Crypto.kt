package com.lomeone.eunoia.kotlin.util.security.crypto

interface Crypto {
    fun encrypt(data: ByteArray): ByteArray
    fun decrypt(encryptedData: ByteArray): ByteArray
}
