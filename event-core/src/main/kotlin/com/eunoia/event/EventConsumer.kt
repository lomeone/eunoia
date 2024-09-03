package com.eunoia.event

import io.cloudevents.CloudEvent

interface EventConsumer {
    fun consume(origin: String, handler: (event: CloudEvent) -> Unit)
}
