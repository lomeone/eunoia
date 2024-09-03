package com.eunoia.event

import io.cloudevents.CloudEvent

interface EventSubscriber {
    fun subscribe(origin: String, handler: (event: CloudEvent) -> Unit)
}
