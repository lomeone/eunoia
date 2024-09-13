package com.eunoia.event.spring.kafka

import com.eunoia.event.EventConsumer
import io.cloudevents.CloudEvent

class KafkaEventConsumer : EventConsumer {
    override fun consume(origin: String, handler: (event: CloudEvent) -> Unit) {
        TODO("Not yet implemented")
    }
}

