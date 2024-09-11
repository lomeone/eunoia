package com.eunoia.kafka.connect.smt.mysql

import com.eunoia.com.eunoia.kafka.connect.smt.common.KafkaEvent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.double
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.long
import kotlinx.serialization.json.longOrNull
import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.connector.ConnectRecord
import org.apache.kafka.connect.data.Struct
import org.apache.kafka.connect.header.ConnectHeaders
import org.apache.kafka.connect.transforms.Transformation
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.ZoneId

class EunoiaOutboxTransformation<R : ConnectRecord<R>> : Transformation<R> {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun configure(p0: MutableMap<String, *>?) {}

    override fun close() {}

    override fun config() = ConfigDef()

    override fun apply(record: R): R {
        try {
            verifyEventRecord(record)

            log.debug("record.value: {}", record.value())
            val values = record.value() as Struct
            log.debug("record.value.after: {}", values["after"])
            val afterValue = values["after"] as Struct

            val event = generateEvent(afterValue)

            log.info("record.value.after.created_at: {}, SMT currentTime: {}",
                LocalDateTime.ofInstant(Instant.ofEpochMilli(afterValue["created_at"] as Long), ZoneId.systemDefault()), now()
            )

            return record.newRecord(
                event.topic,            // 새로운 토픽으로 변경
                null,           // kafka의 기본 파티셔닝 전략 사용
                null,           // 키 스키마 사용하지 않기 때문에 제거
                event.key,              // 새로운 키로 변경
                null,           // 값 스키마 사용하지 않기 때문에 제거
                event.value,            // 새로운 값
                record.timestamp(),     // 원래의 타임스탬프를 유지
                event.headers           // kafka header
            )
        } catch (e: Exception) {
            log.error(e.message)

            return record.newRecord(
                generateDeadLetterTopicName(record),    // 새로운 토픽으로 변경
                record.kafkaPartition(),                // 원래의 파티션을 유지
                record.keySchema(),                     // 원래의 키 스키마를 유지
                record.key(),                           // 원래의 키를 유지
                record.valueSchema(),                   // 값 스키마를 유지
                record.value(),                         // 값 (이 경우 변환됨)
                record.timestamp()                      // 원래의 타임스탬프를 유지
            )
        }
    }

    private fun verifyEventRecord(record: R) {
        record.value() == null && throw Exception("record value is null")

        val value = record.value() as Struct

        value["after"] == null && throw Exception("after record value is null")
    }

    private fun generateEvent(valueStruct: Struct): KafkaEvent {
        log.debug("record.value.after.topic: {}, {}", valueStruct["topic"], valueStruct["topic"]::class)
        log.debug("record.value.after.key: {}, {}", valueStruct["key"], valueStruct["key"]::class)
        log.debug("record.value.after.payload: {}, {}", valueStruct["payload"], valueStruct["payload"]::class)
        log.debug("record.value.after.attributes: {}, {}", valueStruct["attributes"], valueStruct["attributes"]::class)

        val topic = valueStruct["topic"] as String
        val key: String = valueStruct["key"] as String
        val value = Json.parseToJsonElement(valueStruct["payload"] as String)

        val attributes = Json.parseToJsonElement(valueStruct["attributes"] as String)
        val headerData = attributes.toMap()

        val headers = generateHeader(headerData)
        log.debug("topic: {}, key: {}, value: {}, headers: {}", topic, key, value, headers)

        return KafkaEvent(
            topic = topic,
            key = key,
            value = value.toString(),
            headers = headers
        )
    }

    private fun JsonElement.toMap(): Map<String, Any?> = when (this) {
        is JsonObject -> this.mapValues { it.value.toAny() }
        else -> throw IllegalStateException("Expected a JsonObject but was $this")
    }

    private fun JsonElement.toAny(): Any? = when (this) {
        is JsonPrimitive -> when {
            this.isString -> this.content
            this.booleanOrNull != null -> this.boolean
            this.longOrNull != null -> this.long
            this.doubleOrNull != null -> this.double
            else -> this.content
        }
        is JsonArray -> this.map { it.toAny() }
        is JsonObject -> this.toMap()
        JsonNull -> null
    }

    private fun generateHeader(headerData: Map<String, Any?>): ConnectHeaders {
        val headers = ConnectHeaders()

        for ((k, v) in headerData) {
            if (v != null ) {
                log.debug("header value type: value({}) type({})", v, v::class)

                when(v) {
                    is String -> headers.addString(k, v)
                    is Int -> headers.addInt(k, v)
                    is Long -> headers.addLong(k, v)
                    is Double -> headers.addDouble(k, v)
                    is Float -> headers.addFloat(k, v)
                    is Byte -> headers.addByte(k, v)
                    is Short -> headers.addShort(k, v)
                    is Boolean -> headers.addBoolean(k, v)
                    else -> headers.addString(k, v.toString())
                }
            }
        }

        return headers
    }

    private fun generateDeadLetterTopicName(record: R): String {
        try {
            val value = record.value() as Struct
            val topic = value["topic"] as String
            return "$topic-DLQ"
        } catch(e: Exception) {
            return "default-DLQ"
        }
    }
}