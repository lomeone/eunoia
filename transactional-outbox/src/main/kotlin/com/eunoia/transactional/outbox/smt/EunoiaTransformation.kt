package com.eunoia.transactional.outbox.smt

import kotlinx.serialization.json.Json
import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.connector.ConnectRecord
import org.apache.kafka.connect.data.Schema
import org.apache.kafka.connect.data.SchemaBuilder
import org.apache.kafka.connect.data.Struct
import org.apache.kafka.connect.header.ConnectHeaders
import org.apache.kafka.connect.header.Header
import org.apache.kafka.connect.transforms.Transformation

class EunoiaTransformation<R : ConnectRecord<R>> : Transformation<R> {
    override fun configure(p0: MutableMap<String, *>?) {}

    override fun close() {}

    override fun config() = ConfigDef()

    override fun apply(record: R): R {
        try {
            println("record.value: ${record.value()}")

            val values = record.value() as Struct

            println("record.value.after: ${values["after"]}")

            val afterValue = values["after"] as Struct

            println("record.value.after.aggregatetype: ${afterValue["aggregatetype"]}, ${afterValue["aggregatetype"]::class}")
            println("record.value.after.aggregateid: ${afterValue["aggregateid"]}, ${afterValue["aggregateid"]::class}")
            println("record.value.after.aggregateid: ${afterValue["payload"]}, ${afterValue["payload"]::class}")

            val jsonValue = Json.parseToJsonElement(afterValue["payload"] as String)
            println("json payload: ${jsonValue}")

            val headers = ConnectHeaders()
            headers.addString("header", "test")
            headers.addString("meta", "test meta")
            return record.newRecord(
                afterValue["aggregatetype"] as String,  // 새로운 토픽으로 변경
                record.kafkaPartition(),                // 원래의 파티션을 유지
                record.keySchema(),                     // 원래의 키 스키마를 유지
                record.key(),                           // 원래의 키를 유지
                null,                       // 새로운 값 스키마로 변경
                jsonValue.toString(),                   // 새로운 값
                record.timestamp(),                      // 원래의 타임스탬프를 유지
                headers
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
}
