package com.eunoia.event.spring.kafka

import com.eunoia.event.EventProducer
import io.cloudevents.CloudEvent

class KafkaEventProducer : EventProducer {
    override fun produce(destination: String, event: CloudEvent) {
        TODO("Not yet implemented")
    }
}