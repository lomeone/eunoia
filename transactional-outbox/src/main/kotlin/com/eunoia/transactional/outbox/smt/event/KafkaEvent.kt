package com.eunoia.transactional.outbox.smt.event

import org.apache.kafka.connect.header.ConnectHeaders

data class KafkaEvent(
    val topic: String,
    val key: String,
    val value: String,
    val headers: ConnectHeaders
)
