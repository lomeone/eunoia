package com.lomeone.eunoia.event

import io.cloudevents.CloudEvent

interface EventProducer : AutoCloseable {
    fun produce(destination: String, event: CloudEvent)
}
