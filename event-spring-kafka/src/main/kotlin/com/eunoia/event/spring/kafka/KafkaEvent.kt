package com.eunoia.event.spring.kafka

import com.eunoia.event.Event
import org.springframework.kafka.support.Acknowledgment

class KafkaEvent(
    val messageBody: String,
    val acknowledgment: Acknowledgment
) : Event {
    private val _messageHeader: MutableMap<String, String> = mutableMapOf()

    override fun getBody(): String = this.messageBody

    override fun getHeaders(): Map<String, String> = this._messageHeader

    override fun getHeader(key: String): String? = this._messageHeader[key]

    override fun putHeader(key: String, value: String) {
        this._messageHeader[key] = value
    }

    override fun acknowledge() {
        this.acknowledgment.acknowledge()
    }
}
