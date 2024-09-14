package com.eunoia.event.spring.kafka

import com.eunoia.event.EventProducer
import io.cloudevents.CloudEvent
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeaders
import org.springframework.kafka.core.KafkaTemplate

class KafkaEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, CloudEvent>
) : EventProducer {
    override fun produce(destination: String, event: CloudEvent) {
        kafkaTemplate.send(generateProduceRecord(destination, event))
    }

    private fun generateProduceRecord(destination: String, event: CloudEvent) =
        ProducerRecord(destination, null, PARTITION_KEY, event, generateHeader(event))

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
}
