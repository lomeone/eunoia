package com.eunoia.event

import io.cloudevents.CloudEvent

interface EventPublisher {
    fun publish(destination: String, event: CloudEvent)
}
