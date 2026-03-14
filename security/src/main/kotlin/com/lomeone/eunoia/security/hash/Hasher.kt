package com.lomeone.eunoia.security.hash

interface Hasher {
    suspend fun hash(data: ByteArray): ByteArray
    suspend fun verify(data: ByteArray, expectedHash: ByteArray): Boolean
}
