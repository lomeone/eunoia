package com.lomeone.eunoia.kotlin.util.security.crypto.symmetric

import java.security.SecureRandom
import java.util.*
import javax.crypto.spec.IvParameterSpec

fun generateRandomIV(ivSize: Int): IvParameterSpec {
    val iv = ByteArray(ivSize)
    SecureRandom().nextBytes(iv)
    return IvParameterSpec(iv)
}

fun separateIVAndCipherText(cipherText: String, ivSize: Int): Pair<IvParameterSpec, ByteArray> {
    val decodedData = Base64.getDecoder().decode(cipherText)
    return Pair(IvParameterSpec(decodedData.copyOfRange(0, ivSize)), decodedData.copyOfRange(ivSize, decodedData.size))
}
