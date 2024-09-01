package com.eunoia.event.spring.transactional.outbox.event.kafka

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.time.LocalDateTime.now

const val PARTITION_KEY: String = ""

@Entity
class KafkaOutbox(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val topic: String,
    val partitionKey: String,
    val messageBody: String,
    val messageHeaders: String
) {
    @CreatedDate
    val createdAt: LocalDateTime = now()
}
