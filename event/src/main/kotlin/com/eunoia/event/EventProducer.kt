package org.example.com.eunoia.event

interface EventProducer {
    fun produce(destination: String, event: Event)
}
