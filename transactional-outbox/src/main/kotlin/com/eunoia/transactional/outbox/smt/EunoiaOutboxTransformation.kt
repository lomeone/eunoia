package com.eunoia.transactional.outbox.smt

import kotlinx.serialization.json.*
import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.connector.ConnectRecord
import org.apache.kafka.connect.data.Struct
import org.apache.kafka.connect.header.ConnectHeaders
import org.apache.kafka.connect.transforms.Transformation

class EunoiaOutboxTransformation<R : ConnectRecord<R>> : Transformation<R> {
    override fun configure(p0: MutableMap<String, *>?) {}

    override fun close() {}

    override fun config() = ConfigDef()

    override fun apply(record: R): R {
        try {
            println("record.value: ${record.value()}")

            val values = record.value() as Struct

            println("record.value.after: ${values["after"]}")

            val afterValue = values["after"] as Struct

            println("record.value.after.topic: ${afterValue["topic"]}, ${afterValue["topic"]::class}")
            println("record.value.after.key: ${afterValue["key"]}, ${afterValue["key"]::class}")
            println("record.value.after.payload: ${afterValue["payload"]}, ${afterValue["payload"]::class}")
            println("record.value.after.metadata: ${afterValue["metadata"]}, ${afterValue["metadata"]::class}")

            val topic = afterValue["topic"] as String
            println("topic: ${topic}")
            val key: String = afterValue["key"] as String
            println("key: ${key}")
            val value = Json.parseToJsonElement(afterValue["payload"] as String)
            println("value: ${value}")
            val header = Json.parseToJsonElement(afterValue["metadata"] as String)

            val map = header.toMap()

            val headers = ConnectHeaders()
            for ((k, v) in map) {
                println("header value type: value(${v}) type(${v!!::class})")
                when(v) {
                    is String -> headers.addString(k, v)
                    is Int -> headers.addInt(k, v)
                    is Long -> headers.addLong(k, v)
                    is Double -> headers.addDouble(k, v)
                    is Float -> headers.addFloat(k, v)
                    is Byte -> headers.addByte(k, v)
                    is Short -> headers.addShort(k, v)
                    is Boolean -> headers.addBoolean(k, v)
                    else -> headers.addString(k, value.toString())
                }
            }

            headers.addString("header", "test")
            headers.addString("meta", "test meta")
            headers.addString("metadata", header.toString())

            return record.newRecord(
                topic,                          // 새로운 토픽으로 변경
                null,               // kafka의 기본 파티셔닝 전략 사용
                null,                   // 키 스키마 사용하지 않기 때문에 제거
                key,                            // 새로운 키로 변경
                null,                   // 값 스키마 사용하지 않기 때문에 제거
                value.toString(),               // 새로운 값
                record.timestamp(),             // 원래의 타임스탬프를 유지
                headers                         // kafka header
            )

        } catch (e: Exception) {
            println(e.toString())

            return record.newRecord(
                record.topic(),             // 새로운 토픽으로 변경
                record.kafkaPartition(),    // 원래의 파티션을 유지
                record.keySchema(),         // 원래의 키 스키마를 유지
                record.key(),               // 원래의 키를 유지
                record.valueSchema(),       // 값 스키마를 유지
                record.value(),             // 값 (이 경우 변환됨)
                record.timestamp()          // 원래의 타임스탬프를 유지
            )
        }
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
}
