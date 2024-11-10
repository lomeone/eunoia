package com.eunoia.com.lomeone.eunoia.messaging.message

interface MessageProducer {
    fun produce(destination: String, message: Message)
}
