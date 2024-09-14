package com.eunoia.event.kafka

import com.eunoia.event.EventProducer
import io.cloudevents.CloudEvent
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeaders
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.*

class KafkaEventProducer(
    brokerServers: String
) : EventProducer {
    private val kafkaProducer: KafkaProducer<String, CloudEvent>
    init {
        val props = Properties()
        props["bootstrap.servers"] = brokerServers
        props["key.deserializer"] = StringDeserializer::class.java.canonicalName
        props["value.deserializer"] = "io.cloudevents.kafka.CloudEventSerializer"
        kafkaProducer = KafkaProducer(props)
    }

    override fun produce(destination: String, event: CloudEvent) {
        val key = event.id
        val headers = generateHeader(event)

        val record = ProducerRecord(destination, null, key, event, headers)

        kafkaProducer.send(record) { metadata, exception ->
            if (exception != null) {
                println("Error sending message: ${exception.message}")
            } else {
                println("Message sent to topic ${metadata.topic()} with offset ${metadata.offset()}")
            }
        }
    }

    private fun generateHeader(event: CloudEvent): RecordHeaders {
        val headers = RecordHeaders()
        val headerKeys = event.attributeNames

        for (key in headerKeys) {
            val value = event.getAttribute(key)
            if (value != null ) {
                headers.add(key, value.toString().toByteArray())
            }
        }

        return headers
    }

    fun closeProducer() {
        kafkaProducer.close()
    }
}