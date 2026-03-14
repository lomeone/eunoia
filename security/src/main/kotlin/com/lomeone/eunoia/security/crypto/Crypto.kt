package com.lomeone.eunoia.security.crypto

interface Crypto {
    suspend fun encrypt(data: ByteArray): ByteArray
    suspend fun decrypt(encryptedData: ByteArray): ByteArray
}
