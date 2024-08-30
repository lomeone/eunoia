package org.example.com.eunoia.event

interface EventConsumer {
    fun consume(origin: String, handler: (event: Event) -> Unit)
}
