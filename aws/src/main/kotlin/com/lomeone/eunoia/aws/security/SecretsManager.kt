package com.lomeone.eunoia.aws.security

import aws.sdk.kotlin.services.secretsmanager.SecretsManagerClient
import aws.sdk.kotlin.services.secretsmanager.getSecretValue
import com.lomeone.eunoia.aws.security.exception.SecretNotFoundException
import com.lomeone.eunoia.security.secret.SecretRegistry
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

class SecretsManager(
    private val secretsManagerClient: SecretsManagerClient
) : SecretRegistry {

    private val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }

    @OptIn(InternalSerializationApi::class)
    override suspend fun <T : Any> getSecret(secretName: String, targetType: KClass<T>): T =
        json.decodeFromString(targetType.serializer(), getSecretString(secretName))

    @OptIn(InternalSerializationApi::class)
    override suspend fun <T : Any> getSecret(secretName: String, targetType: Class<T>): T =
        json.decodeFromString(targetType.kotlin.serializer(), getSecretString(secretName))

    private suspend fun getSecretString(secretName: String): String =
        secretsManagerClient.getSecretValue { secretId = secretName }.secretString
            ?: throw SecretNotFoundException(mapOf("secretName" to secretName))
}
