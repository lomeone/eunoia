package com.eunoia.event.spring.kafka

import com.eunoia.event.EventPublisher
import io.cloudevents.CloudEvent
import org.apache.kafka.clients.producer.KafkaProducer

class KafkaEventPublisher : EventPublisher {
    override fun publish(destination: String, event: CloudEvent) {
        TODO("Not yet implemented")
    }
}