package com.eunoia.messaging.message

interface MessageProducer {
    fun produce(destination: String, message: Message)
}
