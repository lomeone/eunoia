package com.lomeone.eunoia.event.kafka

import io.cloudevents.CloudEvent
import org.apache.kafka.common.header.internals.RecordHeaders

object KafkaObject {
    fun generateHeader(event: CloudEvent) : RecordHeaders {
        val headers = RecordHeaders()
        val headerKeys = event.attributeNames

        for (key in headerKeys) {
            val value = event.getAttribute(key)
            if (value != null) {
                headers.add(key, value.toString().toByteArray())
            }
        }

        return headers
    }
}
