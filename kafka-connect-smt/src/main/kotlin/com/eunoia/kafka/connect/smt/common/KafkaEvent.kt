package com.eunoia.com.eunoia.kafka.connect.smt.common

import org.apache.kafka.connect.header.ConnectHeaders

data class KafkaEvent(
    val topic: String,
    val key: String,
    val value: String,
    val headers: ConnectHeaders
)
