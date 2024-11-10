package com.lomeone.eunoia.event

import io.cloudevents.CloudEvent

interface EventProducer {
    fun produce(destination: String, event: CloudEvent)
}
