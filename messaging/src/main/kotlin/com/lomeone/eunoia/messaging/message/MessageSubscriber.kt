package com.eunoia.com.lomeone.eunoia.messaging.message

interface MessageSubscriber {
    fun subscribe(origin: String, handler: (message: Message) -> Unit)
}
