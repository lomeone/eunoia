package com.eunoia.event

interface EventPublisher {
    fun publish(destination: String, event: Event)
}
