package com.lomeone.eunoia.security.secret

import kotlin.reflect.KClass

interface SecretRegistry {
    suspend fun <T: Any> getSecret(secretName: String, targetType: KClass<T>): T
    suspend fun <T: Any> getSecret(secretName: String, targetType: Class<T>): T
}
