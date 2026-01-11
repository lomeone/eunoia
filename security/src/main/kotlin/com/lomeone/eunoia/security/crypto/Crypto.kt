package com.lomeone.eunoia.security.crypto

interface Crypto {
    fun encrypt(data: ByteArray): ByteArray
    fun decrypt(encryptedData: ByteArray): ByteArray
}
