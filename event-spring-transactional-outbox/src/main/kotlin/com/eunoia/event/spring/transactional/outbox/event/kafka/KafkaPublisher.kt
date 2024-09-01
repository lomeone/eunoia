package com.eunoia.event.spring.transactional.outbox.event.kafka

import com.eunoia.event.Event
import com.eunoia.event.EventPublisher
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class KafkaPublisher(
    private val kafkaOutboxRepository: KafkaOutboxRepository
) : EventPublisher {
    @Transactional
    override fun publish(destination: String, event: Event) {
        val kafkaOutbox = kafkaOutboxRepository.save(KafkaOutbox(
            topic = destination,
            partitionKey = PARTITION_KEY,
            messageBody = event.getBody(),
            messageHeaders = Json.encodeToString(event.getHeaders())
        ))
    }
}
