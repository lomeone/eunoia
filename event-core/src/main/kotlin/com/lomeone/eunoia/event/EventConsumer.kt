package com.lomeone.eunoia.event

import io.cloudevents.CloudEvent

interface EventConsumer : AutoCloseable {
    fun consume(origin: String, handler: (event: CloudEvent) -> Unit)
}
