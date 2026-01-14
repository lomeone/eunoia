package com.lomeone.eunoia.event.spring.kafka

import com.lomeone.eunoia.event.EventProducer
import io.cloudevents.CloudEvent
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeaders
import org.springframework.kafka.core.KafkaTemplate

class KafkaEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, CloudEvent>
) : EventProducer {
    override fun produce(destination: String, event: CloudEvent) {
        TODO("Not yet implemented")
    }

    private fun generateProduceRecord(destination: String, event: CloudEvent) = {
        TODO("Not yet implemented")
    }

    private fun generateHeader(event: CloudEvent): RecordHeaders {
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

    override fun close() {
        TODO("Not yet implemented")
    }
}
